package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.Drink
import com.tina.mr9.data.Rating
import com.tina.mr9.data.source.Repository
import com.tina.mr9.detail_drink.DetailDrinkViewModel

/**
 * Created by Yuhsin Liao in Jul. 2030.
 *
 * Factory for all ViewModels which need [Drink].
 */
@Suppress("UNCHECKED_CAST")
class DrinkViewModelFactory(
    private val repository: Repository,
    private val drink: Drink?,
    private val rating: Rating?
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(DetailDrinkViewModel::class.java) ->
                    DetailDrinkViewModel(repository, drink, rating)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
