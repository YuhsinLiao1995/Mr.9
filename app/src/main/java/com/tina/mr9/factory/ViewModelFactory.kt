package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.LoginViewModel
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.home.HomeViewModel
import com.tina.mr9.MainViewModel
import com.tina.mr9.friends.FriendsViewModel
import com.tina.mr9.profile.item.BarLikedViewModel
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(stylishRepository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(stylishRepository)

                isAssignableFrom(FriendsViewModel::class.java) ->
                    FriendsViewModel(stylishRepository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(stylishRepository)

                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(stylishRepository)

                isAssignableFrom(BarLikedViewModel::class.java) ->
                    BarLikedViewModel(stylishRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
