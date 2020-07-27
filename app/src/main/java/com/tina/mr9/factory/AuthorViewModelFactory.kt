package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.Mr9Application
import com.tina.mr9.data.User
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.rate.RateViewModel

/**
 * Created by Wayne Chen on 2020-01-15.
 *
 * Factory for all ViewModels which need [Author].
 */
@Suppress("UNCHECKED_CAST")
class AuthorViewModelFactory(
    private val repository: StylishRepository,
    private val user: User
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {



        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}