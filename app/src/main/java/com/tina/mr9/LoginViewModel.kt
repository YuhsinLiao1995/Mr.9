package com.tina.mr9

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [RateFragment].
 */
class LoginViewModel(
    private val repository: StylishRepository
) : ViewModel() {

    val _user = MutableLiveData<User>().apply {

            value = UserManager.user
    }

    val user: LiveData<User>
        get() = _user



    val _drinks = MutableLiveData<Drinks>().apply {
        value = Drinks()
    }

    val drinks: LiveData<Drinks>
        get() = _drinks



    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean>
        get() = _refresh

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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







    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }

    fun refresh() {
        if (!Mr9Application.instance.isLiveDataDesign()) {
            _refresh.value = true
        }
    }



}