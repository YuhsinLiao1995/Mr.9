package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.User
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.others_profile.OthersProfileViewModel
import com.tina.mr9.others_profile.item.OthersLikedViewModel
import com.tina.mr9.others_profile.item.OthersRatingViewModel

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Factory for all ViewModels which need [User].
 */
@Suppress("UNCHECKED_CAST")
class OthersProfileViewModelFactory(
    private val stylishRepository: StylishRepository,
    private val searchUser: User
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(OthersProfileViewModel::class.java)) {
            return OthersProfileViewModel(stylishRepository, searchUser) as T
        }

        if (modelClass.isAssignableFrom(OthersRatingViewModel::class.java)) {
            return OthersRatingViewModel(stylishRepository, searchUser) as T
        }

        if (modelClass.isAssignableFrom(OthersLikedViewModel::class.java)) {
            return OthersLikedViewModel(stylishRepository, searchUser) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}