package com.tina.mr9.detailpage

import android.os.Build
import android.os.UserManager
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.tina.mr9.login.UserManager.user
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
 * The [ViewModel] that is attached to the [DetailFragment].
 */
@RequiresApi(Build.VERSION_CODES.O)
class DetailViewModel(
    private val repository: StylishRepository,
    private val arguments: Drinks?, private val argumentRating: Ratings?
) : ViewModel() {

    // Detail has product data from arguments
    private val _drink = MutableLiveData<Drinks>().apply {arguments.let {
        value = arguments
    }

    }

    val drink: LiveData<Drinks>
        get() = _drink

    private val _theRating = MutableLiveData<Ratings>().apply { argumentRating.let {
        value = argumentRating
    } }

    private val theRating: LiveData<Ratings>
        get() = _theRating

    private val _ratings = MutableLiveData<List<Ratings>>()

    val ratings: LiveData<List<Ratings>>
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
            Toast.makeText(Mr9Application.instance,"Liked", Toast.LENGTH_SHORT).show()
            Logger.d("liked")
            updateLikedBy(true, user.value!!,drink.value!! )
        } else{
            Toast.makeText(Mr9Application.instance,"Unliked", Toast.LENGTH_SHORT).show()
            Logger.d("unliked")
            updateLikedBy(false, user.value!!,drink.value!! )
        }
    }

    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

   var base2String = MutableLiveData<String>()

    var contents2String = MutableLiveData<String>()

    var pairings2String = MutableLiveData<String>()

    var overallRating2String = MutableLiveData<String>()


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
    private val _navigateToDetail = MutableLiveData<Ratings>()

    val navigateToDetail: LiveData<Ratings>
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun main(args: List<String>): String? {
        val delim = ", "
        val res = java.lang.String.join(delim, args)
        println(res)
        return res
    }

    fun array2String(){

        base2String.value = drink.value?.base?.let { main(it) }

        contents2String.value = drink.value?.contents?.let { main(it) }

        pairings2String.value = drink.value?.pairings?.let { main(it) }

        overallRating2String.value = drink.value?.overall_rating?.toDouble()?.toBigDecimal()?.setScale(2, BigDecimal.ROUND_HALF_UP).toString()

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

    fun getTheRatedDrink() {

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

    fun navigateToDetail(ratings: Ratings) {
        _navigateToDetail.value = ratings
    }

//    fun onClick(){
//        if (statusAbout.value == true){
//            Toast.makeText(Mr9Application.instance,"Liked", Toast.LENGTH_SHORT).show()
//            Logger.d("liked")
//        } else{
//            Toast.makeText(Mr9Application.instance,"Unliked", Toast.LENGTH_SHORT).show()
//            Logger.d("unliked")
//        }
//    }

    fun updateLikedBy(likedStatus: Boolean, user: User, drinks:Drinks) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.updateLikedBy(likedStatus,user,drinks)) {
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