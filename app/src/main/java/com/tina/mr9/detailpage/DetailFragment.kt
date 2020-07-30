package com.tina.mr9.detailpage

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tina.mr9.Mr9Application
import com.tina.mr9.NavigationDirections
import com.tina.mr9.databinding.FragmentDetailBinding
import com.tina.mr9.ext.getVmFactory
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger

/**
 * Created by Yuhsin Liao in Jul. 2020
 */
class DetailFragment : Fragment() {

    /**
     * Lazily initialize our [DetailViewModel].
     */
    val viewModel by viewModels<DetailViewModel> {
        getVmFactory(
            DetailFragmentArgs.fromBundle(
                requireArguments()
            ).drinkKey, DetailFragmentArgs.fromBundle(requireArguments()).ratingKey
        )
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerDetailImages.adapter = DetailImagesAdapter()

        val paddingDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,
            resources.displayMetrics
        ).toInt()

        viewModel.drink.observe(viewLifecycleOwner, Observer {it?.let {


            if (viewModel.drink.value?.amtRating!! < 1) {
                binding.noReview.visibility = View.VISIBLE
                Logger.d("no review")
            }
            if (viewModel.drink.value?.amtRating!! < 2) {
                binding.amtRatingsText.text = "rating"
            }
        }
        })



        binding.recyclerRatings.adapter =
            DetailRatingsAdapter(DetailRatingsAdapter.OnClickListener {

                findNavController().navigate(NavigationDirections.navigateToOthersProfileFragment(null,it))
                viewModel.navigateToDetail(it)
            },paddingDp)

        binding.btnRate.setOnClickListener {
            Logger.d("clicked")
            findNavController().navigate(NavigationDirections.navigateToRateFragment(viewModel.drink.value))
        }

        viewModel.drink.observe(viewLifecycleOwner, Observer {
            it?.let {

                Logger.d("viewModel.drink.value = ${viewModel.drink.value?.overall_rating}")
                viewModel.getRatingsResult()

                viewModel.array2String()

                if (viewModel.drink.value?.overall_rating!! > 0f) {
                    binding.niceRatingBar.setRating(viewModel.drink.value?.overall_rating!!)
                } else {
                    binding.niceRatingBar.setRating(0f)
                }




                if (viewModel.drink.value!!.likedBy.contains(UserManager.user.uid)) {
                    viewModel.statusAbout.value = true
                }
            }

        })



//        viewModel.drink.observe(viewLifecycleOwner, Observer {
//            Logger.d("viewModel.argument ${viewModel.drink}")
//            it.let {
//                viewModel.getRatingsResult()
//                Logger.d(" viewModel.getRatingsResult()")
//            }
//        })




        return binding.root


    }


}