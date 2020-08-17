package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.Rating
import com.tina.mr9.data.User
import com.tina.mr9.data.source.Repository
import com.tina.mr9.success.SuccessViewModel

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Factory for all ViewModels which need [Rating].
 */
@Suppress("UNCHECKED_CAST")
class RatingsViewModelFactory(
    private val repository: Repository,
    private val rating: Rating
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SuccessViewModel::class.java)) {
            return SuccessViewModel(repository, rating) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}