package com.tina.mr9.search.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Search
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.data.Result
import com.tina.mr9.search.SearchTypeFilter
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [SearchItemFragment].
 */
class SearchItemViewModel(private val repository: StylishRepository,private val searchType: SearchTypeFilter) : ViewModel() {

    private val _search = MutableLiveData<List<Search>>()

    val search: LiveData<List<Search>>
        get() = _search



    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Search>()

    val navigateToDetail: LiveData<Search>
        get() = _navigateToDetail


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getSearchResult()
    }


    fun getSearchResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING


            val result = searchType.value.let { repository.getSearchList(it) }

            _search.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE

                    Log.d("Tina","result.data = ${result.data}")
                    result.data

                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = Mr9Application.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }





    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun navigateToDetail(search: Search) {
        _navigateToDetail.value = search
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getSearchResult()
        }
    }


}