package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.User
import com.tina.mr9.data.source.Repository
import com.tina.mr9.others_profile.item.OthersBarLikedViewModel
import com.tina.mr9.others_profile.item.OthersLikedViewModel
import com.tina.mr9.others_profile.item.OthersRatingViewModel
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.profile.item.LikedViewModel
import com.tina.mr9.profile.item.MyRatingViewModel

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Factory for all ViewModels which need [User].
 */
@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val repository: Repository,
    private val user: User?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository, user) as T
        }

        if (modelClass.isAssignableFrom(MyRatingViewModel::class.java)) {
            return MyRatingViewModel(repository, user) as T
        }

        if (modelClass.isAssignableFrom(LikedViewModel::class.java)) {
            return LikedViewModel(repository, user) as T
        }

//        if (modelClass.isAssignableFrom(RateViewModel::class.java)) {
//            return RateViewModel(stylishRepository, user) as T
//        }

        if (modelClass.isAssignableFrom(OthersRatingViewModel::class.java)) {
            return OthersRatingViewModel(repository, user) as T
        }

        if (modelClass.isAssignableFrom(OthersLikedViewModel::class.java)) {
            return OthersLikedViewModel(repository, user) as T
        }

        if (modelClass.isAssignableFrom(OthersBarLikedViewModel::class.java)) {
            return OthersBarLikedViewModel(repository, user) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}