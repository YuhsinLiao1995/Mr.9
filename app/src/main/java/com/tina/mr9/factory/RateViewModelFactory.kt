package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.Drink
import com.tina.mr9.data.source.Repository
import com.tina.mr9.rate.RateViewModel

/**
 * Created by Yuhsin Liao in Jul. 2030.
 *
 * Factory for all ViewModels which need [Drink].
 */
@Suppress("UNCHECKED_CAST")
class RateViewModelFactory(
    private val repository: Repository,
    private val drink: Drink?
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(RateViewModel::class.java) ->
                    RateViewModel(repository, drink)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
