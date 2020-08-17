package com.tina.mr9.others_profile.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentOthersLikedBarBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.profile.item.*


class OthersLikedBarFragment : Fragment() {

    companion object {
        fun newInstance() = LikedDrinkFragment()
    }


    /**
     * Lazily initialize our [ProfileViewModel].
     */
    private val viewModel by viewModels<OthersLikedBarViewModel> { getVmFactory(
        OthersLikedBarFragmentArgs.fromBundle(
            requireArguments()
        ).searchUser) }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentOthersLikedBarBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.likedGrid.adapter = LikedBarAdapter(LikedBarAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToBardetailFragment(it))
            viewModel.navigateToDetail
        })

        return binding.root


    }


}
