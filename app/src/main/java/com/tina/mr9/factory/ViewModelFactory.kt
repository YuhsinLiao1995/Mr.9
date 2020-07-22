package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.LoginViewModel
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.home.HomeViewModel
import com.tina.mr9.MainViewModel
import com.tina.mr9.friends.FriendsViewModel

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

                isAssignableFrom(FriendsViewModel::class.java) ->
                    FriendsViewModel(stylishRepository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(stylishRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
