package com.tina.mr9.others_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.tina.mr9.R
import com.tina.mr9.databinding.FragmentOthersProfileBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class OthersProfileFragment : Fragment() {

    /**
     * Lazily initialize our [OthersProfileFragment].
     */
    private val viewModel by viewModels<OthersProfileViewModel> { getVmFactory(OthersProfileFragmentArgs.fromBundle(requireArguments()).searchUser) }
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: OthersPagesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        init()
        val binding = FragmentOthersProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        if (viewModel.searchUser.value!!.followedBy.contains(UserManager.user.uid)){
            viewModel.statusAbout.value = true
//            Logger.d("已追蹤")
        }

        viewModel.searchUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                viewModel.calculate()
                Logger.d("amt_followedBy = ${viewModel.amtFollowedBy.value}")
                Logger.d("amt_following = ${viewModel.amtFollowing.value}")
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagerAdapter = OthersPagesAdapter(childFragmentManager, viewModel.searchUser?.value!!)
        viewPager = view.findViewById(R.id.viewpager_profile_others)
        viewPager.adapter = pagerAdapter
        Logger.d("viewModel.searchUser?.value!! = ${viewModel.searchUser.value}")
    }

    companion object {
        fun newInstance() = OthersProfileFragment()
    }


}