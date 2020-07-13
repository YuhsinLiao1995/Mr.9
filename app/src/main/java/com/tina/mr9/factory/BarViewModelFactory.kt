package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.bardetailpage.BarDetailViewModel
import com.tina.mr9.data.Bar
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.detailpage.DetailViewModel

/**
 * Created by Yuhsin Liao in Jul. 2030.
 *
 * Factory for all ViewModels which need [Drinks].
 */
@Suppress("UNCHECKED_CAST")
class BarViewModelFactory(
    private val stylishRepository: StylishRepository,
    private val bar: Bar
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(BarDetailViewModel::class.java) ->
                    BarDetailViewModel(stylishRepository, bar)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
