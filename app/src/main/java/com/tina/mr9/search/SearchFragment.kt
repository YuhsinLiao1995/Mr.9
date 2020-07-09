package com.tina.mr9.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.databinding.FragmentSearchBinding

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class SearchFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewpagerCatalog.let {
                tabsCatalog.setupWithViewPager(it)
                it.adapter = SearchAdapter(childFragmentManager)
                it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsCatalog))
            }
            return@onCreateView root
        }
    }
}