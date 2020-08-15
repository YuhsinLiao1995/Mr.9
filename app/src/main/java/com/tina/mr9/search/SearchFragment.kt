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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewpagerCatalog.let {
                tabsCatalog.setupWithViewPager(it)
                it.adapter = SearchAdapter(childFragmentManager)
                it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsCatalog))
            }

        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.searchLayout.setOnClickListener {
            binding.scrollViewBar.visibility = View.GONE
            binding.scrollViewDrink.visibility = View.GONE
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
                    binding.scrollViewDrink.visibility = View.VISIBLE
                } else {
                    viewModel.getSearchedBarsResult(searchText)
                    binding.scrollViewBar.visibility = View.VISIBLE
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
                    binding.scrollViewDrink.visibility = View.VISIBLE
                    binding.scrollViewBar.visibility = View.GONE

                } else if (searchText != "" && viewModel.statusType.value == false){
                    binding.scrollViewDrink.visibility = View.GONE
                    binding.scrollViewBar.visibility = View.VISIBLE
                } else{
                    binding.scrollViewDrink.visibility = View.GONE
                    binding.scrollViewBar.visibility = View.GONE
                }

                viewModel.statusType.observe(viewLifecycleOwner, Observer {

                    val searchText = binding.searchEdit.text.toString().trim()

                    if (viewModel.statusType.value == true) {
                        viewModel.getSearchedDrinksResult(searchText)
                        binding.scrollViewDrink.visibility = View.VISIBLE
                    } else {
                        viewModel.getSearchedBarsResult(searchText)
                        binding.scrollViewBar.visibility = View.VISIBLE
                    }
                })

                }
        })

        return binding.root

    }

}