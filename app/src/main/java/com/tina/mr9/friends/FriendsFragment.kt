package com.tina.mr9.friends

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentFriendsBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class FriendsFragment : Fragment() {

    /**
     * Lazily initialize our [FriendsViewModel].
     */

    val viewModel by viewModels<FriendsViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentFriendsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.listView.adapter = FriendsAdapter(FriendsAdapter.OnClickListener {
            viewModel.navigateToDetail
        })

        binding.recyclerPosts.adapter = FriendsRatingAdapter(FriendsRatingAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToDetailFragment(null,it))
            viewModel.navigateToDetail
        }, viewModel.statusAbout.value?: false)



        binding.searchText.addTextChangedListener(object  : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val searchText = binding.searchText.text.toString().trim()

                viewModel.getUserResult(searchText)

                viewModel.searchedUser.observe(viewLifecycleOwner, Observer { it ->
                    it?.let {
                        binding.listView.adapter = FriendsAdapter(FriendsAdapter.OnClickListener {
                            viewModel.navigateToDetail(it)
                        })
                    }
                })

                if (searchText != ""){
                    binding.listView.visibility = View.VISIBLE
                } else {
                    binding.listView.visibility = View.GONE
                }

            }



        } )

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToOthersProfileFragment(it,null))
                viewModel.onDetailNavigated()
            }
        })


        binding.layoutMine.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRateFragment(null))
        }

        return binding.root
    }

}