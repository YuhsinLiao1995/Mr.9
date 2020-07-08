package com.tina.mr9.detailpage

import android.os.Build
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
import java.util.*

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [DetailFragment].
 */
@RequiresApi(Build.VERSION_CODES.O)
class DetailViewModel(
    private val repository: StylishRepository,
    private val arguments: Drinks
) : ViewModel() {

    // Detail has product data from arguments
    private val _drink = MutableLiveData<Drinks>().apply {
        value = arguments
    }

    val drink: LiveData<Drinks>
        get() = _drink

    private val _ratings = MutableLiveData<List<Ratings>>()

    val ratings: LiveData<List<Ratings>>
        get() = _ratings

    val base2String: String? = drink.value?.base?.let { main(it) }
    val baseText: String? = "base : $base2String"

    val contents2String: String? = drink.value?.contents?.let { main(it) }
    val contentsText: String? = "Contents : $contents2String"

    val pairings2String: String? = drink.value?.pairings?.let { main(it) }
    val pairingsText: String? = "Pairings : $pairings2String"

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

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")


        getRatingsResult()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun main(args: List<String>): String? {
        val delim = ", "
        val res = java.lang.String.join(delim, args)
        println(res)
        return res
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

}