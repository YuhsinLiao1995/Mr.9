package com.tina.mr9.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.tina.mr9.NavigationDirections
import com.tina.mr9.R
import com.tina.mr9.databinding.FragmentProfileBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory(ProfileFragmentArgs.fromBundle(requireArguments()).userKey) }
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: PagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel


        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {

                viewModel.calculate()
                UserManager.user = it

            }
        })

        binding.amtFollowing.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToFollowingFragment(UserManager.user,true))
        }

        binding.amtFollowers.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToFollowingFragment(UserManager.user,false))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = PagesAdapter(childFragmentManager)
        viewPager = view.findViewById(R.id.viewpager_profile)
        viewPager.adapter = pagerAdapter
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }


}