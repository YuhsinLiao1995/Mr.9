package com.tina.mr9.friends

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Ratings
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Util.getString
import com.tina.mr9.data.User
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.tina.mr9.data.Result
import com.tina.mr9.login.UserManager

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [FriendsFragment].
 */
class FriendsViewModel(private val repository: StylishRepository) : ViewModel() {

    private val _user = MutableLiveData<List<User>>()

    val user: LiveData<List<User>>
        get() = _user

    private val _searchedUser = MutableLiveData<List<User>>()

    val searchedUser: LiveData<List<User>>
        get() = _searchedUser


    private val _searchText = MutableLiveData<String>()

    val searchText : LiveData<String>
        get() = _searchText

    private val _rating = MutableLiveData<List<Ratings>>()

    val rating : LiveData<List<Ratings>>
        get() = _rating

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



    var statusAbout = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun setAboutStatus(){
        statusAbout.value = !statusAbout.value!!

//        if (statusAbout.value == true){
//            Toast.makeText(Mr9Application.instance,"Liked", Toast.LENGTH_SHORT).show()
//            Logger.d("liked")
//        } else{
//            Toast.makeText(Mr9Application.instance,"Unliked", Toast.LENGTH_SHORT).show()
//            Logger.d("unliked")
//        }
    }

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

    /**
     * Call getMarketingHotsResult() on init so we can display status immediately.
     */
    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")


        Logger.d("UserManager.user 1 = ${UserManager.user}")
        Logger.d("UserManager.user.following 1 = ${UserManager.user.following}")

        getRatingResult()
    }

    /**
     * track [StylishRepository.getMarketingHots]: -> [DefaultStylishRepository] : [StylishRepository] -> [StylishRemoteDataSource] : [StylishDataSource]
     */
    fun getUserResult(searchId : String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getUserResult(searchId)

            Logger.d("result = $result")

            _searchedUser.value = when (result) {
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
                    _error.value = getString(R.string.app_name)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    private fun getRatingResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = UserManager.user.following.let {
                repository.getRatingResult(it)

            }
            Logger.d("UserManager.user.following = ${UserManager.user.following}")


            _rating.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    Logger.d("result.data = ${result.data}")
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

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getRatingResult()
        }
    }

    fun navigateToDetail(searchUser: User) {
        _navigateToDetail.value = searchUser
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }
}