package com.tina.mr9.factory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.list_bar.ListBarViewModel
import com.tina.mr9.data.Drink
import com.tina.mr9.data.Search
import com.tina.mr9.data.source.Repository
import com.tina.mr9.list_drink.ListDrinkViewModel
import com.tina.mr9.search.SearchTypeFilter

/**
 * Created by Yuhsin Liao in Jul. 2030.
 *
 * Factory for all ViewModels which need [Search].
 */
@Suppress("UNCHECKED_CAST")
class ListViewModelFactory(
    private val repository: Repository,
    private val search: Search,
    private val type: SearchTypeFilter
) : ViewModelProvider.Factory {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {

                isAssignableFrom(ListDrinkViewModel::class.java) ->
                    ListDrinkViewModel(repository, search, type)

                isAssignableFrom(ListBarViewModel::class.java) ->
                    ListBarViewModel(repository, search, type)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
