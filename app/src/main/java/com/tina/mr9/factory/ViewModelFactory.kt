package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.home.HomeViewModel
import com.tina.mr9.MainViewModel
import com.tina.mr9.detailpage.DetailViewModel
import com.tina.mr9.friends.FriendsViewModel
import com.tina.mr9.profile.ProfileViewModel
import com.tina.mr9.rate.RateViewModel
import com.tina.mr9.search.SearchViewModel

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val stylishRepository: StylishRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(stylishRepository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(stylishRepository)

                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(stylishRepository)

                isAssignableFrom(RateViewModel::class.java) ->
                    RateViewModel(stylishRepository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(stylishRepository)

                isAssignableFrom(FriendsViewModel::class.java) ->
                    FriendsViewModel(stylishRepository)


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
