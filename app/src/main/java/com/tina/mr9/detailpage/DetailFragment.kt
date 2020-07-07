package com.tina.mr9.detailpage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tina.mr9.databinding.FragmentDetailBinding
import com.tina.mr9.databinding.FragmentHomeBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.home.HomeAdapter

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class DetailFragment : Fragment() {

    /**
     * Lazily initialize our [DetailViewModel].
     */
    val viewModel by viewModels<DetailViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        init()
        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        val adapter = HomeAdapter()

//        binding.recyclerHome.adapter = adapter

//        viewModel.drinks.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//            Log.d("Tina","it = {$it}")
//        })

//        binding.recyclerHome.adapter = HomeAdapter(HomeAdapter.OnClickListener {
//            viewModel.navigateToDetail(it)
//        })
//
//        binding.layoutSwipeRefreshHome.setOnRefreshListener {
//            viewModel.refresh()
//        }
//
//        viewModel.refreshStatus.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                binding.layoutSwipeRefreshHome.isRefreshing = it
//            }
//        })
//
//        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
//                viewModel.onDetailNavigated()
//            }
//        })
//
        return binding.root
    }

//    private fun init() {
//        activity?.let {
//            ViewModelProviders.of(it).get(MainViewModel::class.java).apply {
//                currentFragmentType.value = CurrentFragmentType.HOME
//            }
//        }
//    }
}