package com.tina.mr9

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.data.Drink
import com.tina.mr9.data.Result
import com.tina.mr9.data.User
import com.tina.mr9.data.source.Repository
import com.tina.mr9.login.UserManager
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.CurrentFragmentType
import com.tina.mr9.util.Logger
import com.tina.mr9.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [MainActivity].
 */
class MainViewModel(private val repository: Repository) : ViewModel() {

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    val _user = MutableLiveData<User>().apply {

        value = UserManager.user
    }

    val user: LiveData<User>
        get() = _user


    val _drinks = MutableLiveData<Drink>().apply {
        value = Drink()
    }

    val drink: LiveData<Drink>
        get() = _drinks

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus


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

    fun updateUser(user: User) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            Logger.d("uid = ${user.uid}")

            when (val result = repository.updateUser(user)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                    getUserResult(user.uid)
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = Mr9Application.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }

            }
        }
    }
    fun getUserResult(searchId : String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getMyProfileResult(searchId)

            Logger.d("profile result = $result")

            _user.value = when (result) {
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
                    _error.value = Util.getString(R.string.app_name)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            updateUserManager(_user.value!!)
            _refreshStatus.value = false
        }
    }

    fun updateUserManager(user: User){
        UserManager.user = user
        Logger.d("Usermanager.user = ${UserManager.user}")
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