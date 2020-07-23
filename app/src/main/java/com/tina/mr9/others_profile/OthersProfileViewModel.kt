package com.tina.mr9.others_profile

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.Ratings
import com.tina.mr9.data.Result
import com.tina.mr9.data.User
import com.tina.mr9.data.source.StylishRepository
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
 * The [ViewModel] that is attached to the [OthersProfileFragment].
 */
class OthersProfileViewModel(private val repository: StylishRepository, private val arguments: User) : ViewModel() {
    // After login to Mr.9 server through Google, at the same time we can get user info to provide to display ui

    private val _user = MutableLiveData<User>().apply {
            value = UserManager.user
    }

    val user: LiveData<User>
        get() = _user

    private val _searchUser = MutableLiveData<User>().apply {
        value = arguments
        Logger.d("search profile arguments = $arguments")
    }

    val searchUser: LiveData<User>
        get() = _searchUser

    var statusAbout = MutableLiveData<Boolean>().apply {
        value = false
    }

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
    private val _navigateToDetail = MutableLiveData<Ratings>()

    val navigateToDetail: LiveData<Ratings>
        get() = _navigateToDetail

    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    private var _amtFollowing = MutableLiveData<Int>()

    val amtFollowing: LiveData<Int>
        get() = _amtFollowing

    private var _amtFollowedBy = MutableLiveData<Int>()

    val amtFollowedBy: LiveData<Int>
        get() = _amtFollowedBy

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun setAboutStatus(){
        statusAbout.value = !statusAbout.value!!

        if (statusAbout.value == true){
            Toast.makeText(Mr9Application.instance,"Followed", Toast.LENGTH_SHORT).show()
            Logger.d("liked")
            updateFollowedBy(true, user.value!!,searchUser.value!! )
        } else{
            Toast.makeText(Mr9Application.instance,"UnFollowed", Toast.LENGTH_SHORT).show()
            Logger.d("unliked")
            updateFollowedBy(false, user.value!!,searchUser.value!! )
        }
    }



    init {

        Logger.d("ProfileViewModel, arguments=${arguments}")
//        Logger.d("ProfileViewModel, UserManager.user=${UserManager.user}")
    }

    private fun updateFollowedBy(likedStatus: Boolean, user: User, searchUser: User) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateFollowedBy(likedStatus,user,searchUser)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    getUserResult(searchUser.uid)
                    leave(true)
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

            _searchUser.value = when (result) {
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

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun calculate(){
        _amtFollowing.value = searchUser.value?.following?.size
        _amtFollowedBy.value = searchUser.value?.followedBy?.size

    }



}