package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.Ratings
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.detailpage.DetailViewModel
import com.tina.mr9.rate.RateViewModel

/**
 * Created by Yuhsin Liao in Jul. 2030.
 *
 * Factory for all ViewModels which need [Drinks].
 */
@Suppress("UNCHECKED_CAST")
class RateViewModelFactory(
    private val stylishRepository: StylishRepository,
    private val drinks: Drinks?
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(RateViewModel::class.java) ->
                    RateViewModel(stylishRepository, drinks)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
