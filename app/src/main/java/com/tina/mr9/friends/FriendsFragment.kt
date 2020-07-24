package com.tina.mr9.friends

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.tina.mr9.NavigationDirections
import com.tina.mr9.R
import com.tina.mr9.home.HomeViewModel
import com.tina.mr9.databinding.FragmentFriendsBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.util.Logger
import com.tina.mr9.util.ServiceLocator.stylishRepository

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class FriendsFragment : Fragment() {

    /**
     * Lazily initialize our [FriendsViewModel].
     */
//    private val viewModel by viewModels<FriendsViewModel>
    val viewModel by viewModels<FriendsViewModel> { getVmFactory() }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        init()
        val binding = FragmentFriendsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.listView.adapter = FriendsAdapter(FriendsAdapter.OnClickListener {
            viewModel?.navigateToDetail
        })

        binding.recyclerPosts.adapter = FriendsRatingAdapter(FriendsRatingAdapter.OnClickListener{
            viewModel?.navigateToDetail
            Logger.d("binding.recyclerPosts.adapter")
        }, viewModel.statusAbout.value!!)








        binding.searchText.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = binding.searchText.text.toString().trim()
//                Logger.d("viewModel?.seearchText = ${viewModel?.searchText}")

                viewModel.getUserResult(searchText)
                Logger.d("searchText = $searchText")

                Logger.d("viewModel.searchedUser = ${viewModel.searchedUser}")

                viewModel.searchedUser.observe(viewLifecycleOwner, Observer { it ->
                    it?.let {
                        binding.listView.adapter = FriendsAdapter(FriendsAdapter.OnClickListener {
                            viewModel.navigateToDetail(it)
                        })
                    }
                })

                if (searchText != ""){
                    binding.listView.visibility = View.VISIBLE
                } else {
                    binding.listView.visibility = View.GONE
                }

            }
        } )

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToOthersProfileFragment(it))
                viewModel.onDetailNavigated()
            }
        })





//        binding.recyclerHome.adapter = FriendsAdapter(FriendsAdapter.OnClickListener {
//            viewModel.navigateToDetail(it)
//        })
//
//        binding.layoutSwipeRefreshHome.setOnRefreshListener {
//            viewModel.refresh()
//        }
//
//        viewModel.refreshStatus.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                binding.layoutSwipeRefreshHome.isRefreshing = it
//            }
//        })
//
//        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
//                viewModel.onDetailNavigated()
//            }
//        })
//
        return binding.root
    }

//    private fun loadFirebaseData(searchText : String) {
//
//        if(searchText.isEmpty()){
//
//            FirebaseRecyclerAdapter.cleanup()
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }else {
//
//            viewModel.
//
//
//            val firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")
//
//            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<User, BlankFragment.UsersViewHolder>(
//
//                User::class.java,
//                R.layout.layout_list,
//                BlankFragment.UsersViewHolder::class.java,
//                firebaseSearchQuery
//
//
//            ) {
//                override fun populateViewHolder(viewHolder: BlankFragment.UsersViewHolder, model: User?, position: Int) {
//
//
//                    viewHolder.mview.userName.setText(model?.name)
//                    viewHolder.mview.userStatus.setText(model?.status)
//
//                }
//
//            }
//
//            mRecyclerView.adapter = FirebaseRecyclerAdapter
//
//        }
//    }
}