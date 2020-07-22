package com.tina.mr9.profile.item


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.tina.mr9.databinding.FragmentLikedBinding
import com.tina.mr9.databinding.FragmentMyRatingBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.profile.ProfileFragmentArgs
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.util.Logger

class MyRatingFragment : Fragment() {

    companion object {
        fun newInstance() = MyRatingFragment()
    }

    /**
     * Lazily initialize our [ProfileViewModel].
     */
    private val viewModel by viewModels<MyRatingViewModel> { getVmFactory(MyRatingFragmentArgs.fromBundle(requireArguments()).userKey) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMyRatingBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        Logger.d("test=${viewModel.user.value}")
//        binding.photosGrid.adapter = LikedAdapter(LikedAdapter.OnClickListener{
//            viewModel.displayProductDetails(it)
//        })

        binding.recyclerRatings.adapter = MyRatingAdapter(MyRatingAdapter.OnClickListener{
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