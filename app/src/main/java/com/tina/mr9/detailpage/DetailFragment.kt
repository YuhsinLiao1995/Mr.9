package com.tina.mr9.detailpage

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tina.mr9.Mr9Application
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentDetailBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy

/**
 * Created by Yuhsin Liao in Jul. 2020
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
        Logger.d("viewModel.drink.value?.overall_rating!! = ${viewModel.drink.value?.overall_rating!!}")
//
        if (viewModel.drink.value?.overall_rating!! > 0f) {
            binding.niceRatingBar.setRating(viewModel.drink.value?.overall_rating!!)
            Logger.d("images = ${viewModel.drink.value?.images}")

        }else{

            binding.niceRatingBar.setRating(0f)
            binding.amtRatings.text = "o"
            binding.avgRating.text = "NA"
            Logger.d("-1f")
        }
        binding.recyclerRatings.adapter = DetailRatingsAdapter(DetailRatingsAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        if (viewModel.drink.value!!.likedBy.contains(UserManager.user.uid)){
            viewModel.statusAbout.value = true
//            Logger.d("已讚")
        }






        return binding.root


    }


}