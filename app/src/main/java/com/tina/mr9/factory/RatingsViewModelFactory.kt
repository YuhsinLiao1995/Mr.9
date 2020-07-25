package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.Ratings
import com.tina.mr9.data.User
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.others_profile.OthersProfileViewModel
import com.tina.mr9.others_profile.item.OthersLikedViewModel
import com.tina.mr9.others_profile.item.OthersRatingViewModel
import com.tina.mr9.success.SuccessViewModel

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels which need [User].
 */
@Suppress("UNCHECKED_CAST")
class RatingsViewModelFactory(
    private val stylishRepository: StylishRepository,
    private val ratings: Ratings
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SuccessViewModel::class.java)) {
            return SuccessViewModel(stylishRepository, ratings) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}