package com.tina.mr9.others_profile.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentOthersRatingBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.util.Logger

class OthersRatingFragment : Fragment() {

    companion object {
        fun newInstance() = OthersRatingFragment()
    }

    /**
     * Lazily initialize our [ProfileViewModel].
     */
    private val viewModel by viewModels<OthersRatingViewModel> { getVmFactory(
        OthersRatingFragmentArgs.fromBundle(requireArguments()).searchUser)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentOthersRatingBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Logger.d("test=${viewModel.user.value}")
        
        binding.recyclerRatings.adapter = OthersRatingAdapter(OthersRatingAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToDetailFragment(null,it))
            viewModel.navigateToDetail
        })
        
        return binding.root


    }


}
