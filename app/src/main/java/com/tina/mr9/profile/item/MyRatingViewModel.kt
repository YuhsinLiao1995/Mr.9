package com.tina.mr9.profile.item

//import android.arch.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Drink
import com.tina.mr9.data.Rating
import com.tina.mr9.data.Result
import com.tina.mr9.data.User
import com.tina.mr9.data.source.Repository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyRatingViewModel (private val repository: Repository, private val arguments: User?) : ViewModel() {

    private val _user = MutableLiveData<User>().apply {

            value = com.tina.mr9.login.UserManager.user
    }

    val user: LiveData<User>
        get() = _user

    private val _drink = MutableLiveData<List<Drink>>()

    val drink: LiveData<List<Drink>>
        get() = _drink

    private val _rating = MutableLiveData<List<Rating>>()

    val rating : LiveData<List<Rating>>
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
    private val _navigateToDetail = MutableLiveData<Drink>()

    val navigateToDetail: LiveData<Drink>
        get() = _navigateToDetail


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getRatingResult()
    }

    private fun getRatingResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = user.value.let {
                repository.getMyRatingDrinks(it!!)

            }


            _rating.value = when (result) {
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

    fun navigateToDetail(drink: Drink) {
        _navigateToDetail.value = drink
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getRatingResult()
        }
    }

}