package com.tina.mr9.search.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.component.GridSpacingItemDecoration
import com.tina.mr9.data.Search
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.search.SearchTypeFilter
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [SearchItemFragment].
 */
class SearchItemViewModel(
    private val repository: StylishRepository,
    searchType: SearchTypeFilter // Handle the type for each catalog item
) : ViewModel() {

    private val sourceFactory = PagingDataSourceFactory(searchType)

    val pagingDataTypes: LiveData<PagedList<Search>> = sourceFactory.toLiveData(6, null)

    // Handle load api status
    val status: LiveData<LoadApiStatus> = Transformations.switchMap(sourceFactory.sourceLiveData) {
        it.statusInitialLoad
    }

    // Handle load api error
    val error: LiveData<String> = Transformations.switchMap(sourceFactory.sourceLiveData) {
        it.errorInitialLoad
    }

    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Search>()

    val navigateToDetail: LiveData<Search>
        get() = _navigateToDetail

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val decoration = GridSpacingItemDecoration(
        2,
       Mr9Application.instance.resources.getDimensionPixelSize(R.dimen.space_catalog_grid), true
    )

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            sourceFactory.sourceLiveData.value?.invalidate()
        }
    }

    fun navigateToDetail(search: Search) {
        _navigateToDetail.value = search
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}