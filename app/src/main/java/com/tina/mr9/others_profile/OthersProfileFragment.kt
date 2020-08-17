package com.tina.mr9.others_profile

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
import com.tina.mr9.data.User
import com.tina.mr9.databinding.FragmentOthersProfileBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020.
 */
class OthersProfileFragment : Fragment() {

    /**
     * Lazily initialize our [OthersProfileFragment].
     */
    private val viewModel by viewModels<OthersProfileViewModel> { getVmFactory(
        OthersProfileFragmentArgs.fromBundle(requireArguments()).searchUser,
        OthersProfileFragmentArgs.fromBundle(requireArguments()).rating
    ) }


    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: OthersPagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        init()
        val binding = FragmentOthersProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewPager =  binding.viewpagerProfile



        viewModel.searchUser.observe(viewLifecycleOwner, Observer {
            Logger.d("it = $it")
            it?.let {

                createPager(it)

                viewModel.calculate()
                Logger.d("amt_followedBy = ${viewModel.amtFollowedBy.value}")
                Logger.d("amt_following = ${viewModel.amtFollowing.value}")

                if (viewModel.searchUser.value!!.followedBy.contains(UserManager.user.uid)){
                    viewModel.statusAbout.value = true
//            Logger.d("已追蹤")
                }
            }
        })

        binding.amtFollowing.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToFollowingFragment(viewModel.searchUser.value ?: User(),true))
        }

        binding.amtFollowers.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToFollowingFragment(viewModel.searchUser.value ?: User(),false))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewModel.searchUser.observe(viewLifecycleOwner, Observer {it?.let {
//            createPager(it)
//        }
//        })


    }


    companion object {
        fun newInstance() = OthersProfileFragment()
    }


    fun createPager(searchUser: User){

        pagerAdapter = OthersPagesAdapter(childFragmentManager, searchUser)
//        viewPager = view.findViewById(R.id.viewpager_profile_others)
        viewPager.adapter = pagerAdapter
    }

}