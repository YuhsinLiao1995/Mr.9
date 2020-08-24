package com.tina.mr9.rate

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.kaelli.niceratingbar.OnRatingChangedListener
import com.tina.mr9.MainActivity
import com.tina.mr9.Mr9Application
import com.tina.mr9.NavigationDirections
import com.tina.mr9.data.Bar
import com.tina.mr9.data.Drink
import com.tina.mr9.data.Rating
import com.tina.mr9.databinding.FragmentRateBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.ext.showToast
import com.tina.mr9.util.Logger
import java.io.File
import java.util.*

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */

@Suppress("DEPRECATION")
class RateFragment : Fragment() {

    val viewModel by viewModels<RateViewModel> { getVmFactory(RateFragmentArgs.fromBundle(requireArguments()).drinkKey) }

    var saveUri: Uri? = null

    companion object {
        const val PHOTO_FROM_GALLERY = 0
        const val PHOTO_FROM_CAMERA = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        binding.recyclerImages.adapter = ImageAdapter(ImageAdapter.OnClickListener {

        })

        // request permission of camera and album when init
        permission()


//        fun chipFun(taglist: MutableList<String>, chipGroup: ChipGroup,newChip: EditText ,newTag:  MutableLiveData<String>) {
//
//            for (index in taglist.indices) {
//                val tagName = taglist[index]
//                val chip = Chip(chipGroup.context)
//
//                chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
//                chip.text = tagName
//                chip.textSize = 14f
//                chip.setTextColor(Color.WHITE)
//                val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
//                val chipColors = intArrayOf(Color.parseColor("#3f3a3a"))
//                val chipColorsStateList = ColorStateList(states, chipColors)
//                chip.chipBackgroundColor = chipColorsStateList
//                chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))
//
//                chip.setOnClickListener {
//                    chip.isCloseIconEnabled = !chip.isCloseIconEnabled
//                    //Added click listener on close icon to remove tag from ChipGroup
//                    chip.setOnCloseIconClickListener {
////                        taglist.remove(tagName)
//                        chipGroup.removeView(chip)
//                        Logger.d("$taglist.toString()")
//                        viewModel.taglist.value?.remove(tagName)
//                    }
//                }
//                chip.text = newChip.text
//                chipGroup.addView(chip)
//            }
//            newTag.value = null
//        }

        viewModel.taglist.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.taglist = ${viewModel.taglist.value}")
        })


//        fun updateChip(taglist: MutableList<String>) {
//            for (index in taglist.indices) {
//                val tagName = taglist[index]
//                val chip = Chip(chipGroup.context)
//
//                chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
//                chip.text = tagName
//                chip.textSize = 14f
//                chip.setTextColor(Color.WHITE)
//                val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
//                val chipColors = intArrayOf(Color.parseColor("#999999"))
//                val chipColorsStateList = ColorStateList(states, chipColors)
//                chip.chipBackgroundColor = chipColorsStateList
//                chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))
//
//                chip.setOnClickListener {
//                    chip.isCloseIconEnabled = !chip.isCloseIconEnabled
//                    //Added click listener on close icon to remove tag from ChipGroup
//                    chip.setOnCloseIconClickListener {
//                        taglist.remove(tagName)
//                        chipGroup.removeView(chip)
//                        Logger.d("$taglist.toString()")
//                        viewModel.taglist.value?.remove(tagName)
//                    }
//                }
//
//                viewModel.taglist.value?.plus(taglist)
//                Logger.d("viewModel.taglist.value = ${viewModel.taglist.value}")
//
//                chipGroup.addView(chip)
//            }
//        }



        // manage chip
        val chipGroup = binding.groupProfileTag
        val newChip = binding.conetentInput
        val paddingDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,
            resources.displayMetrics).toInt()

        binding.btnAddChip.setOnClickListener {

            if (viewModel.newtag.value != "") {

                viewModel.taglist.value?.add(newChip.text.toString())

                viewModel.rating.value?.contents = (viewModel.taglist.value ?: listOf())

                viewModel.chipFun(
                    mutableListOf(newChip.text.toString()),
                    chipGroup,
                    newChip,
                    viewModel.newtag,paddingDp
                )
                Logger.d("viewModel.taglist.value = ${viewModel.taglist.value}")
            } else {

                activity.showToast("Please write a content")

            }
        }




        // Click next btn to move forward to review part
        binding.btnNext.setOnClickListener {
            Logger.d("name = ${viewModel.rating.value?.name} bar = ${viewModel.rating.value?.bar}")

            when {
                viewModel.rating.value?.name == "" -> {

                    activity.showToast("Please enter the drink")
                }
                viewModel.rating.value?.bar == "" -> {

                    activity.showToast("Please enter the bar")

                }
                viewModel.images.value?.size == 0 -> {

                    activity.showToast("Please add a photo !")

                }
                else -> {
                    viewModel.setReviewStatus()
                }
            }
        }

        // Observe statusReview to scroll to the Review Part
        viewModel.statusReview.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.statusReview.observe, it=$it")
            it?.let {
                if (it) {

                    binding.rating.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.GONE

                    binding.scrollView.post {
                        binding.scrollView.smoothScrollTo(0, binding.rating.top)
                    }
                }
            }
        })


        // add new chip
        binding.btnAddChipPairing.setOnClickListener {

            val chipGroupPairing = binding.pairingTag
            val newChipPairing = binding.pairingInput

            if (viewModel.newPairingTag.value != "") {

                viewModel.pairingTagList.value?.add(newChipPairing.text.toString())

                viewModel.rating.value?.pairings = (viewModel.pairingTagList.value?: listOf())

                viewModel.chipFun(mutableListOf(newChipPairing.text.toString()),chipGroupPairing,newChipPairing,viewModel.newPairingTag,paddingDp)

                Logger.d("viewModel.taglist.value = ${viewModel.pairingTagList.value}")

            } else{

                activity.showToast("Please enter a pairing")

            }
        }

        binding.btnAddPhoto.setOnClickListener {
            ContextCompat.checkSelfPermission(
                Mr9Application.appContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            toAlbum()
        }

        binding.btnTakePic.setOnClickListener {

            ContextCompat.checkSelfPermission(
                Mr9Application.appContext,
                android.Manifest.permission.CAMERA
            )
            toCamera()
        }


//        viewModel.images.value?.clear()

        viewModel.images.observe(viewLifecycleOwner, Observer {
            Log.i("images", "images = $it")
            it?.let {
                (binding.recyclerImages.adapter as ImageAdapter).submitList(it)
                (binding.recyclerImages.adapter as ImageAdapter).notifyDataSetChanged()

                if(it.size != 0) {
                    binding.recyclerImageitems.visibility = View.VISIBLE
                    binding.recyclerImages.background = null
                }
            }
        })

        binding.niceRatingBar.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onRatingChanged(it)
        })


        binding.niceRatingBarBody.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onBodyChanged(it)
        })

        binding.niceRatingBarSweet.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onSweetnessChanged(it)
        })

        binding.niceRatingBarSour.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onSourChanged(it)
        })



        viewModel.navigateToAddedSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {

                findNavController().navigate(NavigationDirections.navigateToDetailFragment(
                    null,viewModel.rating.value))

                findNavController().navigate(NavigationDirections.navigateToSuccessDialog(viewModel.rating.value!!))
                viewModel.onAddedSuccessNavigated()
            }
        })


//        ContextCompat.checkSelfPermission(
//            Mr9Application.appContext,
//            android.Manifest.permission.CAMERA
//        )


        binding.nameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = binding.nameInput.text.toString().trim()
                Logger.d("viewModel.rating.value?.name = ${viewModel.rating.value?.name}")

                val searchBarText = binding.barInput.text.toString().trim()

                val searchBarAddressText = binding.addressInput.text.toString().trim()

                Logger.d("searchText=$searchText  searchBarText=$searchBarText searchBarAddressText=$searchBarAddressText")

                viewModel.getSearchedRatingDrinksResult(
                    searchText,
                    searchBarText,
                    searchBarAddressText
                )

                Logger.d("viewModel.searchedDrinks = ${viewModel.searchedDrink}")

                viewModel.searchedDrink.observe(viewLifecycleOwner, Observer { it ->
                    it?.let {
                        binding.listViewDrink.adapter =
                            RateSearchedDrinksAdapter(RateSearchedDrinksAdapter.OnClickListener {
                                viewModel.rating.value?.name = it.name
                                Logger.d("viewModel.rating.value?.name = ${viewModel.rating.value?.name}")
                                Logger.d("it.name = ${it.name}")
                                binding.nameInput.setText(it.name)
                                binding.priceInput.setText(it.price.toString())
                                viewModel.taglist.value?.plus(it.contents)
                                viewModel.updateChip(it.contents.toMutableList(),chipGroup,paddingDp)
                                binding.listViewDrink.visibility = View.GONE
                            })


                    }
                })



                if (searchText != "") {

                    binding.listViewDrink.visibility = View.VISIBLE

                } else{

                    binding.listViewDrink.visibility = View.GONE
                    binding.listViewDrink.visibility = View.GONE
                }

            }
        })

        binding.barInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = binding.barInput.text.toString().trim()
                Logger.d("viewModel.rating.value?.name = ${viewModel.rating.value?.bar}")

                viewModel.getSearchedBarsResult(searchText)

                Logger.d("viewModel.searchedBar = ${viewModel.searchedBars}")

                viewModel.searchedBars.observe(viewLifecycleOwner, Observer { it ->
                    it?.let {
                        binding.listViewBar.adapter =
                            RateSearchedBarAdapter(RateSearchedBarAdapter.OnClickListener {
                                binding.addressInput.setText(it.address)
                                binding.barInput.setText(it.name)
                                binding.listViewBar.visibility = View.GONE
                            })
                    }
                })

                if (searchText != "") {
                    binding.listViewBar.visibility = View.VISIBLE

                } else{
                    binding.listViewBar.visibility = View.GONE
                    binding.listViewBar.visibility = View.GONE
                }

            }
        })

        binding.listViewDrink.setOnClickListener() {
            Logger.d("binding.listViewDrink.setOnClickListener")
            binding.listViewDrink.visibility = View.GONE
        }

        binding.listViewBar.setOnClickListener() {
            Logger.d("binding.listViewBar.setOnClickListener")
            binding.listViewBar.visibility = View.GONE
        }

        binding.layoutRatingBackground.setOnClickListener(){
            Logger.d("binding.layoutRatingBackground.onClick")
            binding.listViewDrink.visibility = View.GONE
            binding.listViewBar.visibility = View.GONE
        }

        return binding.root


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }
        Manifest.permission()


    }





    private fun toAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PHOTO_FROM_GALLERY)
    }






    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (saveUri != null) {
            val uriString = saveUri.toString()
            outState.putString("saveUri", uriString)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PHOTO_FROM_GALLERY -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val saveUri = data!!.data
//                        imageView.setImageURI(saveUri)
                        saveUri?.let { viewModel.uploadImage(it) }
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }

                }
            }

            PHOTO_FROM_CAMERA -> {

                Log.d("Tina", "PHOTO_FROM_CAMERA")
                when (resultCode) {

                    Activity.RESULT_OK -> {
                        Log.d("Tina", "RESULT_OK")
//                        Glide.with(this).load(saveUri).into(imageView)
                        Log.d("Tina", "saveUri = $saveUri")
                        saveUri?.let { viewModel.uploadImage(it) }
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }

            }
        }

    }

    private fun toCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val tmpFile = File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            System.currentTimeMillis().toString() + ".jpg"
        )
        val uriForCamera = FileProvider.getUriForFile(
            Mr9Application.appContext,
            "com.tina.mr9.fileprovider",
            tmpFile
        )

        saveUri = uriForCamera
        Log.d("Tina", "saveUri = $saveUri")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForCamera)

        startActivityForResult(intent, PHOTO_FROM_CAMERA)
    }

//    private fun uploadImage(saveUri: Uri) {
//        var firstPhoto = true
//        val filename = UUID.randomUUID().toString()
//        val image = MutableLiveData<String>()
//        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
//        Log.d("TIna", "saveUri = $saveUri")
//        ref.putFile(saveUri!!)
//            .addOnSuccessListener {
//                ref.downloadUrl.addOnSuccessListener {
//                    Log.d("Tina", "it = $it")
//                    image.value = it.toString()
//                    if (firstPhoto) {
//                        viewModel.rating.value?.main_photo = image.value!!
//                        viewModel.rating.value?.images =
//                            listOf(listOf(image.value).toString())
//                        firstPhoto = false
//
//
//
//                    } else {
//                        viewModel.rating.value?.images =
//                            listOf(image.toString())
//                        Log.d("Tina", "not first photo")
//                    }
//                    Log.d(
//                        "Tina",
//                        "viewModel mainImage = ${viewModel.rating.value?.main_photo}; images = ${viewModel.rating.value?.images}"
//                    )
//                    viewModel.images.value?.add(it.toString())
//                    viewModel.images.value = viewModel.images.value
//                    viewModel.rating.value?.images = viewModel.images.value
//
//                }
//            }
//    }




    private fun permission() {
        val permissionList = arrayListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        var size = permissionList.size
        var i = 0
        while (i < size) {
            if (ActivityCompat.checkSelfPermission(
                    Mr9Application.appContext,
                    permissionList[i]
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.removeAt(i)
                i -= 1
                size -= 1
            }
            i += 1
        }
        val array = arrayOfNulls<String>(permissionList.size)
        if (permissionList.isNotEmpty()) ActivityCompat.requestPermissions(
            activity as MainActivity,
            permissionList.toArray(array),
            0
        )

        if (permissionList.isEmpty()){
            dialog()
        }
    }

    private fun dialog() {
        AlertDialog.Builder(Mr9Application.appContext)
            .setTitle("提醒")
            .setMessage("相機功能將無法使用")
    }

}