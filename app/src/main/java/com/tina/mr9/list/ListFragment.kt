package com.tina.mr9.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.data.Ratings
import com.tina.mr9.databinding.FragmentListBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class ListFragment : Fragment() {

    /**
     * Lazily initialize our [ListViewModel].
     */
    val viewModel by viewModels<ListViewModel> { getVmFactory(
        ListFragmentArgs.fromBundle(requireArguments()).SearchKey,
        ListFragmentArgs.fromBundle(requireArguments()).type)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recyclerList.adapter = ListAdapter(ListAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
            Logger.d("viewModel.navigateToDetail = ${viewModel.navigateToDetail}")
        })




        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it,ratingKey = null))
                    viewModel.onDetailNavigated()
            }
        })

        return binding.root

//        viewModel.drink.value?.base?.let { intArrayToArrayString(it) }

    }


}