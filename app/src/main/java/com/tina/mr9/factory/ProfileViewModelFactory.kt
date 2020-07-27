package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.User
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.others_profile.item.OthersLikedViewModel
import com.tina.mr9.others_profile.item.OthersRatingViewModel
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.profile.item.LikedViewModel
import com.tina.mr9.profile.item.MyRatingViewModel
import com.tina.mr9.rate.RateViewModel

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels which need [User].
 */
@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val stylishRepository: StylishRepository,
    private val user: User?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(stylishRepository, user) as T
        }

        if (modelClass.isAssignableFrom(MyRatingViewModel::class.java)) {
            return MyRatingViewModel(stylishRepository, user) as T
        }

        if (modelClass.isAssignableFrom(LikedViewModel::class.java)) {
            return LikedViewModel(stylishRepository, user) as T
        }

//        if (modelClass.isAssignableFrom(RateViewModel::class.java)) {
//            return RateViewModel(stylishRepository, user) as T
//        }

        if (modelClass.isAssignableFrom(OthersRatingViewModel::class.java)) {
            return OthersRatingViewModel(stylishRepository, user) as T
        }

        if (modelClass.isAssignableFrom(OthersLikedViewModel::class.java)) {
            return OthersLikedViewModel(stylishRepository, user) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}