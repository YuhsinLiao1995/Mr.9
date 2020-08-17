package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.Drink
import com.tina.mr9.data.User
import com.tina.mr9.data.source.Repository
import com.tina.mr9.profile.follow.FollowingViewModel

/**
 * Created by Yuhsin Liao in Jul. 2030.
 *
 * Factory for all ViewModels which need [Drink].
 */
@Suppress("UNCHECKED_CAST")
class FollowViewModelFactory(
    private val repository: Repository,
    private val user: User,
    private val follow: Boolean
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(FollowingViewModel::class.java) ->
                    FollowingViewModel(repository, user, follow)


                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
