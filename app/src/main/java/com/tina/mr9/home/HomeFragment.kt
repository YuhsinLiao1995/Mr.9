package com.tina.mr9.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentHomeBinding
import com.tina.mr9.ext.getVmFactory

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class HomeFragment : Fragment() {

    val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerHome.adapter = HomeAdapter(HomeAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it,null))
                viewModel.onDetailNavigated()
            }
        })

        return binding.root
    }

}