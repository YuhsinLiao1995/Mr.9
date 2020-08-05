package com.tina.mr9.profile.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentLikedBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.item.LikedViewModel
import com.tina.mr9.profile.ProfileViewModel


class LikedFragment : Fragment() {

    companion object {
        fun newInstance() = LikedFragment()
    }
    /**
     * Lazily initialize our [ProfileViewModel].
     */
    private val viewModel by viewModels<LikedViewModel> { getVmFactory(LikedFragmentArgs.fromBundle(requireArguments()).userKey) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentLikedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.likedGrid.adapter = LikedAdapter(LikedAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToDetailFragment(it,ratingKey = null))
            viewModel.navigateToDetail
        })


        return binding.root


    }


}
