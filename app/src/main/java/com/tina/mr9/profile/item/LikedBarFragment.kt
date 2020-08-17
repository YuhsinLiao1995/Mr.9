package com.tina.mr9.profile.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentLikedBarBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.ProfileViewModel


class LikedBarFragment : Fragment() {

    companion object {
        fun newInstance() = LikedBarFragment()
    }
    private val viewModel by viewModels<LikedBarViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentLikedBarBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.likedGrid.adapter = LikedBarAdapter(LikedBarAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToBardetailFragment(it))
            viewModel.navigateToDetail
        })

        return binding.root
    }
}
