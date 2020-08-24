package com.tina.mr9.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tina.mr9.data.source.Repository
import com.tina.mr9.search.SearchTypeFilter
import com.tina.mr9.search.item.SearchItemViewModel


@Suppress("UNCHECKED_CAST")
class SearchItemViewModelFactory(
    private val repository: Repository,
    private val searchType: SearchTypeFilter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SearchItemViewModel::class.java) ->
                    SearchItemViewModel(repository,searchType)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}