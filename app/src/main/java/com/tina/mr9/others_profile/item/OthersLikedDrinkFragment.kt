package com.tina.mr9.others_profile.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentOthersLikedDrinkBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.item.LikedDrinkAdapter
import com.tina.mr9.profile.item.LikedDrinkFragment


class OthersLikedDrinkFragment : Fragment() {

    companion object {
        fun newInstance() = LikedDrinkFragment()
    }


    private val viewModel by viewModels<OthersLikedDrinkViewModel> { getVmFactory(
        OthersLikedDrinkFragmentArgs.fromBundle(
            requireArguments()
        ).searchUser) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentOthersLikedDrinkBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.likedGrid.adapter = LikedDrinkAdapter(LikedDrinkAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToDetailFragment(it,ratingKey = null))
            viewModel.navigateToDetail
        })

        parentFragment


        return binding.root


    }


}
