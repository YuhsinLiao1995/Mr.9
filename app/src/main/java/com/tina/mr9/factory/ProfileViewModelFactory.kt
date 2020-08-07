package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.User
import com.tina.mr9.data.source.Repository
import com.tina.mr9.others_profile.item.OthersLikedBarViewModel
import com.tina.mr9.others_profile.item.OthersLikedDrinkViewModel
import com.tina.mr9.others_profile.item.OthersRatingViewModel
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.profile.item.LikedDrinkViewModel
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

        if (modelClass.isAssignableFrom(LikedDrinkViewModel::class.java)) {
            return LikedDrinkViewModel(repository, user) as T
        }

        if (modelClass.isAssignableFrom(OthersRatingViewModel::class.java)) {
            return OthersRatingViewModel(repository, user) as T
        }

        if (modelClass.isAssignableFrom(OthersLikedDrinkViewModel::class.java)) {
            return OthersLikedDrinkViewModel(repository, user) as T
        }

        if (modelClass.isAssignableFrom(OthersLikedBarViewModel::class.java)) {
            return OthersLikedBarViewModel(repository, user) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}