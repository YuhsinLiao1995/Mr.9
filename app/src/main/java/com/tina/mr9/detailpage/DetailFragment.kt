package com.tina.mr9.detailpage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tina.mr9.databinding.FragmentDetailBinding
import com.tina.mr9.ext.getVmFactory

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
        binding.recyclerRatings.adapter = DetailRatingsAdapter(DetailRatingsAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        return binding.root

//        viewModel.drink.value?.base?.let { intArrayToArrayString(it) }

    }


}