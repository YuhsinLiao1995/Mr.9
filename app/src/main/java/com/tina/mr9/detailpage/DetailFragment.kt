package com.tina.mr9.detailpage

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tina.mr9.databinding.FragmentDetailBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger

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
        if (viewModel.drink.value?.overall_rating != -1f) {
            binding.niceRatingBar.setRating(viewModel.drink.value?.overall_rating!!)
            Logger.d("images = ${viewModel.drink.value?.images}")
        }
        binding.recyclerRatings.adapter = DetailRatingsAdapter(DetailRatingsAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        return binding.root

//        viewModel.drink.value?.base?.let { intArrayToArrayString(it) }

    }


}