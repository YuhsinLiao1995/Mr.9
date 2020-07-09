package com.tina.mr9.search.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Search
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.search.SearchTypeFilter
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.tina.mr9.data.Result
import com.tina.mr9.util.Util.getString

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class PagingDataSource(val type: SearchTypeFilter) : PageKeyedDataSource<String, Search>() {

    // init load status for observe

    private val _statusInitialLoad = MutableLiveData<LoadApiStatus>()

    val statusInitialLoad: LiveData<LoadApiStatus>
        get() = _statusInitialLoad

    // init load error for observe
    private val _errorInitialLoad = MutableLiveData<String>()

    val errorInitialLoad: LiveData<String>
        get() = _errorInitialLoad

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    /**
     * Initial load api
     */
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Search>) {
//        Logger.d("[${type.value}] loadInitial") // open it if you want to observe status

        coroutineScope.launch {

            _statusInitialLoad.value = LoadApiStatus.LOADING

            val result = Mr9Application.instance.stylishRepository
                .getSearchList(type = type.value)
            when (result) {
                is Result.Success -> {
                    _errorInitialLoad.value = null
                    _statusInitialLoad.value = LoadApiStatus.DONE
//                    Logger.d("[${type.value}] loadInitial.paging=${result.data.paging}") // open it if you want to observe status
                    result.data.let {

                        Logger.d("loadInitial.result=$it") // open it if you want to observe status
                        callback.onResult(it, null, "")
                    }
                }
                is Result.Fail -> {
                    _errorInitialLoad.value = result.error
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _errorInitialLoad.value = result.exception.toString()
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                else -> {
                    _errorInitialLoad.value = getString(R.string.you_know_nothing)
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    /**
     * After initial load, it will according to paging key to load api
     */
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Search>) {
//        Logger.d("[${type.value}] loadAfter.key=${params.key}") // open it if you want to observe status

//        coroutineScope.launch {
//            val result = Mr9Application.instance.stylishRepository
//                .getSearchList(type = type.value)
//            when (result) {
//                is Result.Success -> {
//                    Logger.d("[${type.value}] loadAfter.result=${result.data}") // open it if you want to observe status
////                    Logger.d("[${type.value}] loadAfter.paging=${result.data.paging}") // // open it if you want to observe status
//                    result.data.let { callback.onResult(it, result.data.toString()) }
//                }
//            }
//        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Search>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}