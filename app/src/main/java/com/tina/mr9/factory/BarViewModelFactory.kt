package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.Bar
import com.tina.mr9.data.source.Repository
import com.tina.mr9.detail_bar.DetailBarViewModel

/**
 * Created by Yuhsin Liao in Jul. 2030.
 *
 * Factory for all ViewModels which need [Bar].
 */
@Suppress("UNCHECKED_CAST")
class BarViewModelFactory(
    private val repository: Repository,
    private val bar: Bar
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(DetailBarViewModel::class.java) ->
                    DetailBarViewModel(repository, bar)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
