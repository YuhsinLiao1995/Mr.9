package com.tina.mr9.search

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.MainActivity
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.data.source.Repository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [SearchFragment].
 */
@RequiresApi(Build.VERSION_CODES.O)
class SearchViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _searchText = MutableLiveData<String>()

    val searchText : LiveData<String>
        get() = _searchText

    private val _searchedDrinks = MutableLiveData<List<Drink>>()

    val searchedDrink: LiveData<List<Drink>>
        get() = _searchedDrinks

    private val _searchedBars = MutableLiveData<List<Bar>>()

    val searchedBars: LiveData<List<Bar>>
        get() = _searchedBars

    private val _theRating = MutableLiveData<Rating>()


    val theRating: LiveData<Rating>
        get() = _theRating

    private val _ratings = MutableLiveData<List<Rating>>()

    val rating: LiveData<List<Rating>>
        get() = _ratings

    private val _user = MutableLiveData<User>().apply {
        value = com.tina.mr9.login.UserManager.user
    }

    val user: LiveData<User>
        get() = _user

    var statusType = MutableLiveData<Boolean>().apply {
        value = true
    }

    fun setSearchStatus() {
        statusType.value = !statusType.value!!
        Logger.d("setAboutStatus")
        if (statusType.value == true) {
            Toast.makeText(Mr9Application.instance, "Search Drink", Toast.LENGTH_SHORT).show()
            Logger.d("statusType.value = ${statusType.value}")
        } else {
            Toast.makeText(Mr9Application.instance, "Search Bar", Toast.LENGTH_SHORT).show()
            Logger.d("statusType.value = ${statusType.value}")
        }
    }

    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave



//    fun qw(){
//        var f = 34.232323f
//        var b: BigDecimal = BigDecimal(f)
//        var f1: Float = b.setScale(2, BigDecimal.ROUND_HALF_UP)
//            .toFloat()
//
//        Logger.d("f1 = $f1")
//    }


    // b.setScale(2, BigDecimal.ROUND_HALF_UP)


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

    // Handle navigation to detail
    private val _navigateToBarDetail = MutableLiveData<Bar>()

    val navigateToBarDetail: LiveData<Bar>
        get() = _navigateToBarDetail

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun main(args: List<String>): String? {
        val delim = ", "
        val res = java.lang.String.join(delim, args)
        println(res)
        return res
    }



    fun getSearchedDrinksResult(searchedText: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = searchedText.let { repository.getSearchedDrinksResult(it) }

            _searchedDrinks.value = when (result) {
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

    fun getSearchedBarsResult(searchedText: String) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = searchedText.let { repository.getSearchedBarsResult(it) }

            _searchedBars.value = when (result) {
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



    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun navigateToDetail(drink: Drink) {
        _navigateToDetail.value = drink
    }

    fun navigateToBarDetail(bar: Bar) {
        _navigateToBarDetail.value = bar
    }


    fun updateLikedBy(likedStatus: Boolean, user: User, drink: Drink) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateLikedBy(likedStatus, user, drink)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

}