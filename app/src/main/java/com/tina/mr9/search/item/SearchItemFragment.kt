package com.tina.mr9.search.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tina.mr9.databinding.FragmentSearchItemBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.search.SearchAdapter
import com.tina.mr9.search.SearchTypeFilter

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class SearchItemFragment(private val searchType: SearchTypeFilter) : Fragment() {

    /**
     * Lazily initialize our [SearchItemViewModel].
     */
    private val viewModel by viewModels<SearchItemViewModel> { getVmFactory(searchType) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentSearchItemBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        binding.recyclerSearchItem.adapter = SearchItemAdapter(SearchItemAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        binding.layoutSwipeRefreshCatalogItem.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it != LoadApiStatus.LOADING)
                    binding.layoutSwipeRefreshCatalogItem.isRefreshing = false
            }
        })

        return binding.root
    }
}