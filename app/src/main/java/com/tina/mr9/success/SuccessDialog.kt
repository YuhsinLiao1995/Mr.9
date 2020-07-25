package com.tina.mr9.success

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
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.kaelli.niceratingbar.OnRatingChangedListener
import com.tina.mr9.MainActivity
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.databinding.DialogSuccessBinding
import com.tina.mr9.databinding.FragmentRateBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.rate.RateAdapter
import com.tina.mr9.rate.RateFragmentArgs
import com.tina.mr9.rate.RateViewModel
import com.tina.mr9.util.Logger
import com.xw.repo.BubbleSeekBar
import com.xw.repo.BubbleSeekBar.OnProgressChangedListenerAdapter
import java.io.File
import java.util.*

/**
 * Created by Wayne Chen in Jul. 2019.
 */

@Suppress("DEPRECATION")
class SuccessDialog : AppCompatDialogFragment() {

    /**
     * Lazily initialize our [RateViewModel].
     */
    val viewModel by viewModels<SuccessViewModel> { getVmFactory(
        SuccessDialogArgs.fromBundle(
            requireArguments()
        ).ratingKey) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoginDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        init()
        val binding = DialogSuccessBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel



        binding.root.setOnClickListener() {
            dismiss()
            Log.d("ifinit", "close") }




        return binding.root
    }}







