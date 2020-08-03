package com.tina.mr9.bardetailpage

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.data.*
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [BarDetailFragment].
 */
@RequiresApi(Build.VERSION_CODES.O)
class BarDetailViewModel(
    private val repository: StylishRepository,
    private val arguments: Bar
) : ViewModel() {

    var statusAbout = MutableLiveData<Boolean>().apply {
        value = true
    }

    fun setAboutStatus(){
        statusAbout.value = !statusAbout.value!!
    }

    var statusLike = MutableLiveData<Boolean>().apply {
        value = false
    }



    fun setLikeStatus(){
        statusLike.value = !statusLike.value!!

        if (statusLike.value == true){
            Toast.makeText(Mr9Application.instance,"Liked", Toast.LENGTH_SHORT).show()
            Logger.d("liked")
            updateLikedBarBy(true,bar.value!!)
        } else{
            Toast.makeText(Mr9Application.instance,"Unliked", Toast.LENGTH_SHORT).show()
            Logger.d("unliked")
            updateLikedBarBy(false,bar.value!!)
        }
    }



    var statusMenu = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun setMenuStatus(){
        statusMenu.value = !statusMenu.value!!
    }

    // Detail has product data from arguments
    private val _bar = MutableLiveData<Bar>().apply {
        value = arguments
    }

    val bar: LiveData<Bar>
        get() = _bar

    private val _drinks = MutableLiveData<List<Drinks>>()

    val drinks: LiveData<List<Drinks>>
        get() = _drinks


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


        getDrinksResult()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun main(args: List<String>): String? {
        val delim = ", "
        val res = java.lang.String.join(delim, args)
        println(res)
        return res
    }


    fun getDrinksResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = bar.value?.name?.let { repository.getBarDrinks(it) }

            _drinks.value = when (result) {
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

    fun updateLikedBarBy(likedStatus: Boolean, bar: Bar) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateLikedBarBy(likedStatus, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
//                    leave(true)
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

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun navigateToDetail(drinks: Drinks) {
        _navigateToDetail.value = drinks
    }

}