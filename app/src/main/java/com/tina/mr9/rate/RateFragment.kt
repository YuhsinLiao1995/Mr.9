package com.tina.mr9.rate

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
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
import android.widget.EditText
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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.storage.FirebaseStorage
import com.kaelli.niceratingbar.OnRatingChangedListener
import com.tina.mr9.MainActivity
import com.tina.mr9.Mr9Application
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentRateBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger
import java.io.File
import java.util.*

/**
 * Created by Wayne Chen in Jul. 2019.
 */

@Suppress("DEPRECATION")
class RateFragment : Fragment() {

    /**
     * Lazily initialize our [RateViewModel].
     */
    val viewModel by viewModels<RateViewModel> { getVmFactory(RateFragmentArgs.fromBundle(requireArguments()).drinkKey) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        init()
        val binding = FragmentRateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        val chipGroup = binding.groupProfileTag
        val newChip = binding.conetentInput
        val paddingDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,
            resources.displayMetrics
        ).toInt()
        fun chipFun(taglist: MutableList<String>, chipGroup: ChipGroup,newChip: EditText ,newTag:  MutableLiveData<String>) {

            for (index in taglist.indices) {
                val tagName = taglist[index]
                val chip = Chip(chipGroup.context)

                chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
                chip.text = tagName
                chip.textSize = 14f
                chip.setTextColor(Color.WHITE)
                val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
                val chipColors = intArrayOf(Color.parseColor("#3f3a3a"))
                val chipColorsStateList = ColorStateList(states, chipColors)
                chip.chipBackgroundColor = chipColorsStateList
                chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))

                chip.setOnClickListener {
                    chip.isCloseIconEnabled = !chip.isCloseIconEnabled
                    //Added click listener on close icon to remove tag from ChipGroup
                    chip.setOnCloseIconClickListener {
//                        taglist.remove(tagName)
                        chipGroup.removeView(chip)
                        Logger.d("$taglist.toString()")
                        viewModel.taglist.value?.remove(tagName)
                    }
                }
                chip.text = newChip.text
                chipGroup.addView(chip)
            }
            newTag.value = null
        }

        viewModel.taglist.observe(viewLifecycleOwner, Observer {
            Logger.d("viewModel.taglist = ${viewModel.taglist.value}")
        })


        fun updateChip(taglist: MutableList<String>) {
            for (index in taglist.indices) {
                val tagName = taglist[index]
                val chip = Chip(chipGroup.context)

                chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
                chip.text = tagName
                chip.textSize = 14f
                chip.setTextColor(Color.WHITE)
                val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
                val chipColors = intArrayOf(Color.parseColor("#999999"))
                val chipColorsStateList = ColorStateList(states, chipColors)
                chip.chipBackgroundColor = chipColorsStateList
                chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))

                chip.setOnClickListener {
                    chip.isCloseIconEnabled = !chip.isCloseIconEnabled
                    //Added click listener on close icon to remove tag from ChipGroup
                    chip.setOnCloseIconClickListener {
                        taglist.remove(tagName)
                        chipGroup.removeView(chip)
                        Logger.d("$taglist.toString()")
                        viewModel.taglist.value?.remove(tagName)
                    }
                }

                viewModel.taglist.value?.plus(taglist)
                Logger.d("viewModel.taglist.value = ${viewModel.taglist.value}")

                chipGroup.addView(chip)
            }
        }





        binding.btnAddChip.setOnClickListener {

            if (viewModel.newtag.value != "") {

                viewModel.taglist.value?.add(newChip.text.toString())

                viewModel.rating.value?.pairings = (viewModel.taglist.value ?: listOf())

                chipFun(
                    mutableListOf(newChip.text.toString()),
                    chipGroup,
                    newChip,
                    viewModel.newtag
                )
                Logger.d("viewModel.taglist.value = ${viewModel.taglist.value}")
            } else {
                Toast.makeText(
                    Mr9Application.appContext,
                    "Please write a content",
                    Toast.LENGTH_LONG
                ).show()


            }
        }

        val scrollView = binding.scrollView
        val rating = binding.rating

//        viewModel.statusReview.observe(viewLifecycleOwner, Observer {
//            if (it == true){
//                scrollView.post{
//                    scrollView.scrollTo(0,   rating.top);
//                    Logger.d("scroll to rate")
//                }
//            }
//        })


        binding.btnNext.setOnClickListener {
            viewModel.setReviewStatus()
            viewModel.statusReview.observe(viewLifecycleOwner, Observer {
                Logger.d("viewModel.statusReview.observe, it=$it")
                it?.let {
                    if (it) {
                        binding.rating.visibility = View.VISIBLE
                        binding.btnNext.visibility = View.GONE

                        scrollView.post {
//                            scrollView.scrollTo(0, rating.top);
                            Logger.d("scroll to rate")
                            scrollView.smoothScrollTo(0, rating.top)
                        }
                    }

                }
            })
        }


        val chipGroupPairing = binding.pairingTag
        val newChipPairing = binding.pairingInput

        binding.btnAddChipPairing.setOnClickListener {
            if (viewModel.newPairingTag.value != "") {

                viewModel.pairingTagList.value?.add(newChipPairing.text.toString())

                viewModel.rating.value?.pairings = (viewModel.pairingTagList.value?: listOf())

                chipFun(mutableListOf(newChipPairing.text.toString()),chipGroupPairing,newChipPairing,viewModel.newPairingTag)

                Logger.d("viewModel.taglist.value = ${viewModel.pairingTagList.value}")

            } else{
                Toast.makeText(Mr9Application.appContext,"Please write a pairing",Toast.LENGTH_LONG).show()
            }
        }





        binding.recyclerImages.adapter = RateAdapter(RateAdapter.OnClickListener {})
        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }
        permission()
        binding.btnAddPhoto.setOnClickListener {
            toAlbum()
        }

        binding.btnTakePic.setOnClickListener {
            ContextCompat.checkSelfPermission(
                Mr9Application.appContext,
                android.Manifest.permission.CAMERA
            )
            toCamera()
        }
        viewModel.images.value?.clear()

        viewModel.images.observe(viewLifecycleOwner, Observer {
            Log.i("images", "images = $it")
            it?.let {
                (binding.recyclerImages.adapter as RateAdapter).submitList(it)
                (binding.recyclerImages.adapter as RateAdapter).notifyDataSetChanged()

                if(it.size != 0) {
                    binding.recyclerImageitems.visibility = View.VISIBLE
                    binding.recyclerImages.background = null
                }
            }
        })

        binding.niceRatingBar.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onRatingChanged(it)
            Log.d("Tina","it = $it")
        })

        binding.niceRatingBarSweet.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onSeekSweetChanged(it)
            Log.d("Tina","it = $it")
        })

        binding.niceRatingBarSour.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onSeekSourChanged(it)
            Log.d("Tina","it = $it")
        })

        binding.buttonPublish.setOnClickListener(){
            if (viewModel.rating.value?.name == "" ){
                Toast.makeText(Mr9Application.appContext,"Please enter the drink ",Toast.LENGTH_LONG).show()


            }else if (viewModel.rating.value?.bar == ""){

                Toast.makeText(Mr9Application.appContext,"Please enter the bar ",Toast.LENGTH_LONG).show()

            }else if (viewModel.images.value?.size == 0){

                Toast.makeText(Mr9Application.appContext,"Please add a photo! ",Toast.LENGTH_LONG).show()
            }
            else {
                Logger.d("viewModel._drink.value ${viewModel._drink.value}")
                viewModel.publish(viewModel.rating?.value!!, viewModel.drink.value!!,viewModel.bar.value!!)
            }

        }

        viewModel.navigateToAddedSuccess.observe(viewLifecycleOwner, Observer {
            it?.let {

                findNavController().navigate(NavigationDirections.navigateToDetailFragment(
                    null,viewModel.rating.value))

                findNavController().navigate(NavigationDirections.navigateToSuccessDialog(viewModel.rating.value!!))
                viewModel.onAddedSuccessNavigated()
            }
        })


        ContextCompat.checkSelfPermission(
            Mr9Application.appContext,
            android.Manifest.permission.CAMERA
        )


        binding.nameInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = binding.nameInput.text.toString().trim()
                Logger.d("viewModel.rating.value?.name = ${viewModel.rating.value?.name}")


                    viewModel.getSearchedDrinksResult(searchText)


                Logger.d("viewModel.searchedUser = ${viewModel.searchedDrinks}")

                viewModel.searchedDrinks.observe(viewLifecycleOwner, Observer { it ->
                    it?.let {
                        binding.listViewDrink.adapter =
                            RateSearchedDrinksAdapter(RateSearchedDrinksAdapter.OnClickListener {
                                viewModel.rating.value?.name = it.name
                                Logger.d("viewModel.rating.value?.name = ${viewModel.rating.value?.name}")
                                Logger.d("it.name = ${it.name}")
                                binding.nameInput.setText(it.name)
                                binding.priceInput.setText(it.price.toString())
                                viewModel.taglist.value?.plus(it.contents)
                                updateChip(it.contents.toMutableList())
                                binding.listViewDrink.visibility = View.GONE
                            })
                       Logger.d("viewModel.rating.value?.name = ${viewModel.rating.value?.name}")


                        Logger.d("viewModel.rating.value?.name = ${viewModel.rating.value?.name}")


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

//        binding.bubbleSeekBarSweet.setProgress(5f)
//
//        binding.bubbleSeekBarSour.setProgress(5f)
//
//        binding.bubbleSeekBarSweet.onProgressChangedListener = object : OnProgressChangedListenerAdapter() {
//            override fun onProgressChanged(
//                bubbleSeekBar: BubbleSeekBar,
//                progress: Int,
//                progressFloat: Float,
//                fromUser: Boolean
//            ) {
//                Logger.d("onProgressChanged")
//
//                val test: Float = progressFloat
//
//                Logger.d("progressFloat = $progressFloat")
////                binding.demo4ProgressText11.text = s
//            }
//
//            override fun getProgressOnActionUp(
//                bubbleSeekBar: BubbleSeekBar,
//                progress: Int,
//                progressFloat: Float
//            ) {
//
//                viewModel.onSeekSweetChanged(progressFloat)
//            }
//
//            override fun getProgressOnFinally(
//                bubbleSeekBar: BubbleSeekBar,
//                progress: Int,
//                progressFloat: Float,
//                fromUser: Boolean
//            ) {
//                viewModel._rating.value?.sweet = progressFloat
//            }
//        }
//
//        binding.bubbleSeekBarSour.onProgressChangedListener = object : OnProgressChangedListenerAdapter() {
//            override fun onProgressChanged(
//                bubbleSeekBar: BubbleSeekBar,
//                progress: Int,
//                progressFloat: Float,
//                fromUser: Boolean
//            ) {
//                Logger.d("onProgressChanged")
//            }
//
//            override fun getProgressOnActionUp(
//                bubbleSeekBar: BubbleSeekBar,
//                progress: Int,
//                progressFloat: Float
//            ) {
//
//                viewModel.onSeekSourChanged(progressFloat)
//            }
//
//            override fun getProgressOnFinally(
//                bubbleSeekBar: BubbleSeekBar,
//                progress: Int,
//                progressFloat: Float,
//                fromUser: Boolean
//            ) {
////                binding.demo4ProgressText3.setText(s)
//                Logger.d("progressFloat = $progressFloat")
//
////                viewModel._rating.value?.sour = progressFloat
////                Logger.d("viewModel._rating.value?.sour = ${viewModel._rating.value?.sour}")
//            }
//        }

//        var pg = viewModel._rating.value?.alcohol_ABV



//        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
////                discount.setText(progress.toString() + "%")
////                after.setText("${editText.text.toString().toFloat() * progress/100}")
//                pg= progress
//                Logger.d("pg = ${progress.toString()}")
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//
//                Logger.d("pg = ${pg}")
//            }
//        })

        return binding.root


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            saveUri = Uri.parse(savedInstanceState.getString("saveUri"))
        }
        Manifest.permission()

//

    }

    var saveUri: Uri? = null

    companion object {
        val PHOTO_FROM_GALLERY = 0
        val PHOTO_FROM_CAMERA = 1
    }

    fun toAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, PHOTO_FROM_GALLERY)
    }


    fun toCamera() {
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

    fun permission() {
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
                        saveUri?.let { uploadImage(it) }
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
                        saveUri?.let { uploadImage(it) }
                    }
                    Activity.RESULT_CANCELED -> {
                        Log.wtf("getImageResult", resultCode.toString())
                    }
                }

            }
        }

    }

    private fun uploadImage(saveUri: Uri) {
        var firstPhoto = true
        val filename = UUID.randomUUID().toString()
        val image = MutableLiveData<String>()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        Log.d("TIna", "saveUri = $saveUri")
        ref.putFile(saveUri!!)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Tina", "it = $it")
                    image.value = it.toString()
                    if (firstPhoto) {
                        viewModel.rating.value?.main_photo = image.value!!
                        viewModel.rating.value?.images =
                            listOf(listOf(image.value).toString())
                        firstPhoto = false



                    } else {
                        viewModel.rating.value?.images =
                            listOf(image.toString())
                        Log.d("Tina", "not first photo")
                    }
                    Log.d(
                        "Tina",
                        "viewModel mainImage = ${viewModel.rating.value?.main_photo}; images = ${viewModel.rating.value?.images}"
                    )
                    viewModel.images.value?.add(it.toString())
                    viewModel.images.value = viewModel.images.value
                    viewModel.rating.value?.images = viewModel.images.value





                }
            }
    }

    fun dailog() {
        AlertDialog.Builder(Mr9Application.appContext)
            .setTitle("提醒")
            .setMessage("相機功能將無法使用")
    }


//    private fun init() {
//        activity?.let {
//            ViewModelProviders.of(it).get(MainViewModel::class.java).apply {
//                currentFragmentType.value = CurrentFragmentType.HOME
//            }
//        }
//    }
}