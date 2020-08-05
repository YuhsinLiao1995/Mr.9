package com.tina.mr9.list_bar

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
import com.tina.mr9.databinding.FragmentListBarBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class ListBarFragment : Fragment() {

    /**
     * Lazily initialize our [ListBarViewModel].
     */
    val viewModel by viewModels<ListBarViewModel> { getVmFactory(
        ListBarFragmentArgs.fromBundle(requireArguments()).SearchKey,
        ListBarFragmentArgs.fromBundle(requireArguments()).type)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentListBarBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerList.adapter = ListBarAdapter(ListBarAdapter.OnClickListener {
            viewModel.navigateToBarDetail(it)
            Logger.d("viewModel.navigateToBarDetail = ${viewModel.navigateToBarDetail}")
        })

        viewModel.navigateToBarDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToBardetailFragment(it))
                viewModel.onBarDetailNavigated()
                Logger.d("it = $it")
            }
        })
        return binding.root
    }
}