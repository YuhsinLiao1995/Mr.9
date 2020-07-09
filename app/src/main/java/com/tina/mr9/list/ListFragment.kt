package com.tina.mr9.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tina.mr9.databinding.FragmentDetailBinding
import com.tina.mr9.databinding.FragmentListBinding
import com.tina.mr9.detailpage.DetailFragmentArgs
import com.tina.mr9.detailpage.DetailImagesAdapter
import com.tina.mr9.detailpage.DetailRatingsAdapter
import com.tina.mr9.ext.getVmFactory

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class ListFragment : Fragment() {

    /**
     * Lazily initialize our [ListViewModel].
     */
    val viewModel by viewModels<ListViewModel> { getVmFactory(
        DetailFragmentArgs.fromBundle(
            requireArguments()
        ).drinkKey)  }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

//        binding.recyclerList.adapter = ListAdapter(ListAdapter.OnClickListener {
//            viewModel.navigateToDetail(it)
//        })

        return binding.root

//        viewModel.drink.value?.base?.let { intArrayToArrayString(it) }

    }


}