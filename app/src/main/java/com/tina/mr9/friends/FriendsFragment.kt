package com.tina.mr9.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tina.mr9.home.HomeViewModel
import com.tina.mr9.databinding.FragmentFriendsBinding
import com.tina.mr9.util.ServiceLocator.stylishRepository

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class FriendsFragment : Fragment() {

    /**
     * Lazily initialize our [HomeViewModel].
     */
//    private val viewModel by viewModels<FriendsViewModel>
    private val viewModel  = stylishRepository?.let { FriendsViewModel(it) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        init()
        val binding = FragmentFriendsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


//        binding.recyclerHome.adapter = FriendsAdapter(FriendsAdapter.OnClickListener {
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