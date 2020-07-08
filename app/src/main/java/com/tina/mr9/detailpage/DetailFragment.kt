package com.tina.mr9.detailpage

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tina.mr9.databinding.FragmentDetailBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger.d
import com.tina.mr9.util.Logger.i
import java.util.*
import java.util.logging.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class DetailFragment : Fragment() {

    /**
     * Lazily initialize our [DetailViewModel].
     */
    val viewModel by viewModels<DetailViewModel> { getVmFactory(DetailFragmentArgs.fromBundle(requireArguments()).drinkKey)  }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerDetailImages.adapter = DetailImagesAdapter()

        return binding.root

//        viewModel.drink.value?.base?.let { intArrayToArrayString(it) }

    }

//    private fun intArrayToArrayString(array: List<String>): String? {
//        val arrayString: String = Arrays.toString(array)
//        println(arrayString)
//        return arrayString
//    }




//    fun main(args: Array<String>) {
//        val list = Arrays.asList("A", "B", "C")
//        val delim = "-"
//        val res = java.lang.String.join(delim, list)
//        println(res)
//    }


}