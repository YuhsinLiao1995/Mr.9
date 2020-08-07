package com.tina.mr9.detail_drink

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import java.math.BigDecimal

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [DetailDrinkFragment].
 */
@RequiresApi(Build.VERSION_CODES.O)
class DetailDrinkViewModel(
    private val repository: Repository,
    private val arguments: Drink?, private val argumentRating: Rating?
) : ViewModel() {

    private val _drink = MutableLiveData<Drink>().apply {
        arguments.let {
            value = arguments
        }

    }

    val drink: LiveData<Drink>
        get() = _drink

    private val _theRating = MutableLiveData<Rating>().apply { argumentRating.let {
        value = argumentRating
    } }

    private val theRating: LiveData<Rating>
        get() = _theRating

    private val _ratings = MutableLiveData<List<Rating>>()

    val rating: LiveData<List<Rating>>
        get() = _ratings

    private val _user = MutableLiveData<User>().apply {
        value = com.tina.mr9.login.UserManager.user
    }

    val user: LiveData<User>
        get() = _user

    var statusAbout = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun setAboutStatus(){
        statusAbout.value = !statusAbout.value!!

        if (statusAbout.value == true){
            Logger.d("liked")
            updateLikedBy(true, user.value!!,drink.value!! )
        } else{
            Logger.d("unliked")
            updateLikedBy(false, user.value!!,drink.value!! )
        }
    }

   var base2String = MutableLiveData<String>()

    var contents2String = MutableLiveData<String>()

    var pairings2String = MutableLiveData<String>()

    var overallRating2String = MutableLiveData<String>()

    fun array2String(){


        overallRating2String.value = drink.value?.overall_rating?.toDouble()?.toBigDecimal()?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()

    }


    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    private val _navigateToDetail = MutableLiveData<Rating>()

    val navigateToDetail: LiveData<Rating>
        get() = _navigateToDetail

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        if (arguments == null){
            getTheRatedDrink()
        }


    }

    fun getRatingsResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = drink.value?.id?.let { repository.getRatings(it) }

            _ratings.value = when (result) {
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

    private fun getTheRatedDrink() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = theRating.value?.let { repository.getTheRatedDrink(it) }

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

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun navigateToDetail(rating: Rating) {
        _navigateToDetail.value = rating
    }


    private fun updateLikedBy(likedStatus: Boolean, user: User, drink: Drink) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateLikedBy(likedStatus, user, drink)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
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

}