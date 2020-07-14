package com.tina.mr9.rate

import android.media.Rating
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Util.getString
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [RateFragment].
 */
class RateViewModel(
    private val repository: StylishRepository
//    private val user: User?
) : ViewModel() {

//    private val _rating = MutableLiveData<Ratings>().apply {
//        if (user != null) {
//            value = Ratings(
//                author = user.name
//            )
//        }
//    }
//
//    val article: LiveData<Ratings>
//        get() = _rating
//
//    private val _leave = MutableLiveData<Boolean>()
//
//    val leave: LiveData<Boolean>
//        get() = _leave
//
//    // status: The internal MutableLiveData that stores the status of the most recent request
//    private val _status = MutableLiveData<LoadApiStatus>()
//
//    val status: LiveData<LoadApiStatus>
//        get() = _status
//
//    // error: The internal MutableLiveData that stores the error of the most recent request
//    private val _error = MutableLiveData<String>()
//
//    val error: LiveData<String>
//        get() = _error
//
//    // Create a Coroutine scope using a job to be able to cancel when needed
//    private var viewModelJob = Job()
//
//    // the Coroutine runs using the Main (UI) dispatcher
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
//
//    /**
//     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
//     * Retrofit service to stop.
//     */
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
//
//    init {
//        Logger.i("------------------------------------")
//        Logger.i("[${this::class.simpleName}]${this}")
//        Logger.i("------------------------------------")
//    }
//
//    fun publish(user: User) {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            when (val result = repository.publish(article)) {
//                is kotlin.Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    leave(true)
//                }
//                is kotlin.Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is kotlin.Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = PublisherApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }
}