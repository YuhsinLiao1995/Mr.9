package com.tina.mr9.profile.item


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentBarlikedBinding
import com.tina.mr9.databinding.FragmentLikedBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.item.LikedViewModel
import com.tina.mr9.profile.ProfileViewModel


class BarLikedFragment : Fragment() {

    companion object {
        fun newInstance() = BarLikedFragment()
    }


    /**
     * Lazily initialize our [ProfileViewModel].
     */
    private val viewModel by viewModels<BarLikedViewModel> { getVmFactory() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentBarlikedBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.likedGrid.adapter = BarLikedAdapter(BarLikedAdapter.OnClickListener{
            findNavController().navigate(NavigationDirections.navigateToBardetailFragment(it))
            viewModel.navigateToDetail
        })




//        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner, Observer {
//            if ( null != it ) {
//                // Must find the NavController from the Fragment
//                this.findNavController().navigate(WomwnProductFragmentDirections.actionGlobalDetailPageFragment(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                viewModel.displayProductDetailsComplete()
//            }
//        })


        return binding.root


    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(WomwnProductViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
