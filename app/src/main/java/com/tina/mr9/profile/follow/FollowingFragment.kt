package com.tina.mr9.profile.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentFollowingBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.friends.FriendsAdapter
import com.tina.mr9.others_profile.OthersProfileFragmentArgs

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class FollowingFragment : Fragment() {

    /**
     * Lazily initialize our [FollowingViewModel].
     */
    private val viewModel by viewModels<FollowingViewModel> { getVmFactory(
        FollowingFragmentArgs.fromBundle(requireArguments()).user,
        FollowingFragmentArgs.fromBundle(requireArguments()).follow) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentFollowingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.listView.adapter = FriendsAdapter(FriendsAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToOthersProfileFragment(it,null))
        })


        return binding.root
    }

    companion object {
        fun newInstance() = FollowingFragment()
    }

}