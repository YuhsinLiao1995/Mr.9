package com.tina.mr9.search

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentSearchBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel> { getVmFactory() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val binding = FragmentSearchBinding.inflate(inflater, container, false)


        val binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewpagerCatalog.let {
                tabsCatalog.setupWithViewPager(it)
                it.adapter = SearchAdapter(childFragmentManager)
                it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsCatalog))
            }
//            return@onCreateView root
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.map.setOnClickListener {
            Logger.d("clicked")
        }
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = binding.searchEdit.text.toString().trim()
                Logger.d("viewModel?.seearchText = ${viewModel.searchText}")

                if (viewModel.statusType.value == true) {
                    viewModel.getSearchedDrinksResult(searchText)
                } else {
                    viewModel.getSearchedBarsResult(searchText)
                }

                Logger.d("viewModel.searchedUser = ${viewModel.searchedDrink}")

                viewModel.searchedDrink.observe(viewLifecycleOwner, Observer { it ->
                    it?.let {
                        binding.listViewDrink.adapter =
                            SearchedDrinksAdapter(SearchedDrinksAdapter.OnClickListener {
                                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it,null))
                                viewModel.navigateToDetail(it)
                            })
                    }
                })

                viewModel.searchedBars.observe(viewLifecycleOwner, Observer { it ->
                    it?.let {
                        binding.listViewBar.adapter =
                            SearchedBarAdapter(SearchedBarAdapter.OnClickListener {
                                findNavController().navigate(NavigationDirections.navigateToBardetailFragment(it))
                                viewModel.navigateToBarDetail(it)
                            })
                    }
                })

                if (searchText != "" && viewModel.statusType.value == true) {
                    binding.scrolllistViewDrink.visibility = View.VISIBLE
                    binding.scrolllistViewBar.visibility = View.GONE

                } else if (searchText != "" && viewModel.statusType.value == false){
                    binding.scrolllistViewDrink.visibility = View.GONE
                    binding.scrolllistViewBar.visibility = View.VISIBLE
                } else{
                    binding.scrolllistViewDrink.visibility = View.GONE
                    binding.scrolllistViewBar.visibility = View.GONE
                }

            }
        })

        return binding.root

    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//
//        viewpagerCatalog.let {
//            tabsCatalog.setupWithViewPager(it)
//            it.adapter = SearchAdapter(childFragmentManager)
////                it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsCatalog))
//        }
//
//        SearchAdapter = PagesAdapter(childFragmentManager)
//        viewPager = view.findViewById(R.id.viewpager_catalog)
//        viewPager.adapter = SearchAdapter
//
////        PagerAdapter = PagesAdapter(childFragmentManager)
////        viewPager = view.findViewById(R.id.viewpager_profile)
////        viewPager.adapter = PagerAdapter
//    }
}