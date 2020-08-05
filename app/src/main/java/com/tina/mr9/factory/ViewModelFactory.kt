package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.LoginViewModel
import com.tina.mr9.data.source.Repository
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
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)

                isAssignableFrom(FriendsViewModel::class.java) ->
                    FriendsViewModel(repository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                isAssignableFrom(SearchViewModel::class.java) ->
                    SearchViewModel(repository)

                isAssignableFrom(BarLikedViewModel::class.java) ->
                    BarLikedViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
