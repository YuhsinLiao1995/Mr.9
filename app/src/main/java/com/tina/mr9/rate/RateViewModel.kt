package com.tina.mr9.rate

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.data.source.StylishRepository
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
class RateViewModel(
    private val repository: StylishRepository,
    private val user: User
) : ViewModel() {

    val _rating = MutableLiveData<Ratings>().apply {
        value = Ratings(
            author = user.uid
        )
    }

    val images = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }

    val rating: LiveData<Ratings>
        get() = _rating

    val _drinks = MutableLiveData<Drinks>().apply {
        value = Drinks()
    }

    val drinks: LiveData<Drinks>
        get() = _drinks

    val _bar = MutableLiveData<Bar>().apply {
        value = Bar()
    }

    val bar: LiveData<Bar>
        get() = _bar


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

    fun publish(ratings: Ratings, drinks: Drinks, bar: Bar) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publish(ratings, drinks, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                }
                is Result.DrinkNotExist -> {
                    publish2(ratings, drinks, bar)
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

    fun publish2(ratings: Ratings, drinks: Drinks, bar: Bar) {
        Log.d("Tina", "ifcalled")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addDrinks(ratings, drinks, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    publish(ratings, drinks, bar)
                    leave(true)
                }
                is Result.BarNotExist -> {
                    addBar(ratings, drinks, bar)
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

    fun addBar(ratings: Ratings, drinks: Drinks, bar: Bar) {
        Log.d("Tina", "ifcalledbar")

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addBar(ratings, drinks, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    publish(ratings, drinks, bar)
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

    fun bindingDrink() {
        _drinks.value?.name = rating.value!!.name
        _drinks.value?.bar = rating.value!!.bar
        _drinks.value?.contents = rating.value!!.contents
        _drinks.value?.base = rating.value!!.base
        _drinks.value?.contents = rating.value!!.contents
        _drinks.value?.category = rating.value!!.category
        _drinks.value?.pairings = rating.value!!.pairings
        _drinks.value?.strong = rating.value!!.strong
        _drinks.value?.sweet = rating.value!!.sweet
        _drinks.value?.sour = rating.value!!.sour
        _drinks.value?.take_again = rating.value!!.take_again
        _drinks.value?.main_image = rating.value!!.main_photo
        _drinks.value?.images = rating.value!!.images!!

        _bar.value?.name = drinks.value!!.bar
        _bar.value?.main_image = drinks.value!!.main_image
        _bar.value?.images = drinks.value!!.images

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

    fun onRatingChanged(rating: Float) {
        _rating.value?.overall_rating = rating
        Logger.d("_rating.value?.overall_rating = ${_rating.value?.overall_rating}")
    }

    fun onSeekSourChanged(rating: Float) {
        _rating.value?.sour = rating
        Logger.d("_rating.value?.sour = ${_rating.value?.sour}")
    }

    fun onSeekSweetChanged(rating: Float) {
        _rating.value?.sweet = rating
        Logger.d("_rating.value?.acidic = ${_rating.value?.sweet}")
    }

}