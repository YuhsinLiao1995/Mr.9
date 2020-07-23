package com.tina.mr9.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.R
import com.tina.mr9.data.Result
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.data.User
import com.tina.mr9.login.UserManager
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Logger
import com.tina.mr9.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [ProfileFragment].
 */
class ProfileViewModel(private val stylishRepository: StylishRepository, private val arguments: User?) : ViewModel() {
    // After login to Mr.9 server through Google, at the same time we can get user info to provide to display ui

    private val _user = MutableLiveData<User>().apply {
        arguments?.let {
            value = it
            Logger.d("arguments.image = ${arguments.image}")
        }

    }

    val user: LiveData<User>
        get() = _user


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
    private val _navigateToDetail = MutableLiveData<User>()

    val navigateToDetail: LiveData<User>
        get() = _navigateToDetail


    private var _amtFollowing = MutableLiveData<Int>()

    val amtFollowing: LiveData<Int>
        get() = _amtFollowing

    private var _amtFollowedBy = MutableLiveData<Int>()

    val amtFollowedBy: LiveData<Int>
        get() = _amtFollowedBy

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

        user.value?.uid?.let { getUserResult(it) }
    }

    fun getUserResult(searchId : String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = stylishRepository.getMyProfileResult(searchId)

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
            _refreshStatus.value = false
        }
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
        }
    }

    fun calculate(){
        _amtFollowing.value = user.value?.following?.size
        _amtFollowedBy.value = user.value?.followedBy?.size

    }



}