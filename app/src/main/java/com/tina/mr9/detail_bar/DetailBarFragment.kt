package com.tina.mr9.detail_bar

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentDetailBarBinding
import com.tina.mr9.detail_drink.DetailDrinkViewModel
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020
 */
class DetailBarFragment() : Fragment() {

    /**
     * Lazily initialize our [DetailDrinkViewModel].
     */
    val viewModel by viewModels<DetailBarViewModel> {
        getVmFactory(
            DetailBarFragmentArgs.fromBundle(
                requireArguments()
            ).barKey
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetailBarBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerDetailImages.adapter = DetailBarImagesAdapter()
        binding.recyclerDrinks.adapter = DetailBarDrinksAdapter(DetailBarDrinksAdapter.OnClickListener{
            viewModel.navigateToDetail(it)
            Logger.d("viewModel.navigateToDetail = ${viewModel.navigateToDetail}")
        })

        if (viewModel.bar.value?.overallRating!! > 0f) {
            binding.niceRatingBar.setRating(viewModel.bar.value?.overallRating!!)
        } else {
            binding.niceRatingBar.setRating(0f)
        }

        if (viewModel.bar.value!!.likedBy.contains(UserManager.user.uid)) {
            viewModel.statusAbout.value = true
        }
        Logger.d("viewModel.bar.value?.overallRating!! = ${viewModel.bar.value?.overallRating!!}")


        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it,null))
                viewModel.onDetailNavigated()
            }
        })

        return binding.root

    }
}