package com.tina.mr9.rate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tina.mr9.databinding.FragmentRateBinding
import com.tina.mr9.ext.getVmFactory

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class RateFragment : Fragment() {

    /**
     * Lazily initialize our [HomeViewModel].
     */
    val viewModel by viewModels<RateViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        init()
        val binding = FragmentRateBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel



//        binding.recyclerHome.adapter = SearchAdapter(SearchAdapter.OnClickListener {
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