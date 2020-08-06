package com.tina.mr9.success

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.tina.mr9.R
import com.tina.mr9.databinding.DialogSuccessBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.rate.RateViewModel

/**
 * Created by Yuhsin Liao in Jul. 2020.
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


        binding.successDialog.setOnClickListener() {
            dismiss()
            Log.d("ifinit", "close") }

        binding.closeBtn.setOnClickListener() {
            dismiss()
            Log.d("binding.closeBtn", "close") }






        return binding.root
    }}







