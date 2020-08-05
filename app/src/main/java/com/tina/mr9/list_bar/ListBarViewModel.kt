package com.tina.mr9.list_bar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.source.Repository
import com.tina.mr9.data.*
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.search.SearchTypeFilter
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class ListBarViewModel(
    private val repository: Repository,
    private val arguments: Search,
    private val type: SearchTypeFilter
) : ViewModel() {

    private val _search = MutableLiveData<Search>().apply {
        value = arguments
    }

    val search: LiveData<Search>
        get() = _search

    private val _bars = MutableLiveData<List<Bar>>()

    val bars: LiveData<List<Bar>>
        get() = _bars

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
    private val _navigateToBarDetail = MutableLiveData<Bar>()

    val navigateToBarDetail: LiveData<Bar>
        get() = _navigateToBarDetail

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getBarResult()

    }

    private fun getBarResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = search.value?.name?.let {
                repository.getBarList(it, type.value)
            }

            _bars.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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

    fun onBarDetailNavigated() {
        _navigateToBarDetail.value = null
    }

    fun navigateToBarDetail(bar: Bar) {
        _navigateToBarDetail.value = bar
    }

}