package com.tina.mr9.rate

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.kaelli.niceratingbar.OnRatingChangedListener
import com.tina.mr9.MainActivity
import com.tina.mr9.Mr9Application
import com.tina.mr9.data.User
import com.tina.mr9.databinding.FragmentRateBinding
import com.tina.mr9.ext.getVmFactory
import kotlinx.android.synthetic.main.fragment_rate.*
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
    val viewModel by viewModels<RateViewModel> { getVmFactory(user = User()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        init()
        val binding = FragmentRateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

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

        viewModel.images.observe(viewLifecycleOwner, Observer {
            Log.i("images", "images = $it")
            it?.let {
                (binding.recyclerImages.adapter as RateAdapter).submitList(it)
                (binding.recyclerImages.adapter as RateAdapter).notifyDataSetChanged()
            }
        })



        binding.niceRatingBar.setOnRatingChangedListener(OnRatingChangedListener {
            viewModel.onRatingChanged(it)
            Log.d("Tina","it = $it")
        })


        binding.buttonPublish.setOnClickListener(){

            viewModel.bindingDrink()
            viewModel.publish(viewModel.rating.value!!, viewModel.drinks.value!!)
        }



        ContextCompat.checkSelfPermission(
            Mr9Application.appContext,
            android.Manifest.permission.CAMERA
        )

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
                        imageView.setImageURI(saveUri)
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
                        Glide.with(this).load(saveUri).into(imageView)
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