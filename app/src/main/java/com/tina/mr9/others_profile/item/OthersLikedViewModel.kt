package com.tina.mr9.others_profile.item


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.login.UserManager
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OthersLikedViewModel(private val repository: StylishRepository, private val arguments: User?) : ViewModel() {

    // After login to Mr.9 server through Google, at the same time we can get user info to provide to display ui
    private val _user = MutableLiveData<User>().apply {
            value = UserManager.user
    }

    val user: LiveData<User>
        get() = _user

    private val _searchUser = MutableLiveData<User>().apply {
        value = arguments
        Logger.d("OthersLikedViewModel argument = $arguments")

    }

    val searchUser: LiveData<User>
        get() = _searchUser

    private val _drink = MutableLiveData<List<Drinks>>()

    val drinks: LiveData<List<Drinks>>
        get() = _drink

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
    private val _navigateToDetail = MutableLiveData<Drinks>()

    val navigateToDetail: LiveData<Drinks>
        get() = _navigateToDetail


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getLikedDrinks()
    }

    fun getLikedDrinks() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = searchUser.value.let {
                repository.getLikedDrinks(it!!)
            }
            Logger.d("repository.getOthersLikedDrinks(it!!)")
            Logger.d("search uid = ${searchUser.value?.uid} ")
            Logger.d("search result = $result ")


            _drink.value = when (result) {
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun navigateToDetail(drinks: Drinks) {
        _navigateToDetail.value = drinks
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getLikedDrinks()
        }
    }

}

