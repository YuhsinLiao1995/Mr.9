package com.tina.mr9.profile.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentMyRatingBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.util.Logger

class MyRatingFragment : Fragment() {

    companion object {
        fun newInstance() = MyRatingFragment()
    }

    private val viewModel by viewModels<MyRatingViewModel> { getVmFactory(MyRatingFragmentArgs.fromBundle(requireArguments()).userKey) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMyRatingBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerRatings.adapter = MyRatingAdapter(MyRatingAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToDetailFragment(null,it))
            viewModel.navigateToDetail
        })

        binding.noRatingLayout.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRateFragment(null))
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            Logger.i("MyRatingFragment, viewModel.user.observe, it=$it")
        })

        viewModel.rating.observe(viewLifecycleOwner, Observer {
            Logger.i("MyRatingFragment, viewModel.rating.observe, it=${it.size}")
        })

        return binding.root
    }
}
