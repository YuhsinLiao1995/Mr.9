package com.tina.mr9.bardetailpage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentBardetailBinding
import com.tina.mr9.detailpage.DetailFragmentArgs
import com.tina.mr9.detailpage.DetailImagesAdapter
import com.tina.mr9.detailpage.DetailViewModel
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020
 */
class BarDetailFragment() : Fragment() {

    /**
     * Lazily initialize our [DetailViewModel].
     */
    val viewModel by viewModels<BarDetailViewModel> {
        getVmFactory(
            BarDetailFragmentArgs.fromBundle(
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

        val binding = FragmentBardetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerDetailImages.adapter = BarDetailImagesAdapter()
        binding.recyclerDrinks.adapter = BarDetailDrinksAdapter(BarDetailDrinksAdapter.OnClickListener{
            viewModel.navigateToDetail(it)
            Logger.d("viewModel.navigateToDetail = ${viewModel.navigateToDetail}")
        })



        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                viewModel.onDetailNavigated()
            }
        })

        return binding.root

    }
}