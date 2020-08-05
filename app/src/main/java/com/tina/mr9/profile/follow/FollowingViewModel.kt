package com.tina.mr9.profile.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.R
import com.tina.mr9.data.Result
import com.tina.mr9.data.source.Repository
import com.tina.mr9.data.User
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
 * The [ViewModel] that is attached to the [FollowingFragment].
 */
class FollowingViewModel(
    private val repository: Repository,
    user: User,
    follow: Boolean
) : ViewModel() {
    // After login to Mr.9 server through Google, at the same time we can get user info to provide to display ui

    private val _user = MutableLiveData<User>().apply {
        value = user
    }

    val user: LiveData<User>
        get() = _user

    private val _following = MutableLiveData<List<User>>()

    val following: LiveData<List<User>>
        get() = _following


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
        if (follow) {
            user.following?.let { getUserResult(it) }
        } else{
            user.followedBy?.let { getUserResult(it) }
        }
    }

    fun getUserResult(followList : List<String>) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getFollowUser(followList)

            Logger.d("result = $result")

            _following.value = when (result) {
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




}