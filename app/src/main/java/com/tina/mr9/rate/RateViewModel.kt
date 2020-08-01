package com.tina.mr9.rate

import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Bar
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.Ratings
import com.tina.mr9.data.Result
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.login.UserManager
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [RateFragment].
 */
class RateViewModel(
    private val repository: StylishRepository,
    private val arguments: Drinks?
) : ViewModel() {

    val _rating = MutableLiveData<Ratings>().apply {

            value = Ratings(
                author = UserManager.user.uid,
                authorName = UserManager.user.name,
                authorImage = UserManager.user.image
            )

        arguments?.let {
            value = Ratings(
                name = arguments.name,
                bar = arguments.bar,
                contents = arguments.contents,
                base = arguments.base,
                category = arguments.category,
                author = UserManager.user.uid,
                authorName = UserManager.user.name,
                authorImage = UserManager.user.image
            )


        }

    }

    private val _searchedDrinks = MutableLiveData<List<Drinks>>()

    val searchedDrinks: LiveData<List<Drinks>>
        get() = _searchedDrinks

    private val _searchedBars = MutableLiveData<List<Bar>>()

    val searchedBars: LiveData<List<Bar>>
        get() = _searchedBars

    val images = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }

    val taglist = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }

    val newtag = MutableLiveData<String>().apply {
        value = String()
    }


    val pairingTagList = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }

    val newPairingTag = MutableLiveData<String>().apply {
        value = String()
    }


    val rating: LiveData<Ratings>
        get() = _rating

    val _drink = MutableLiveData<Drinks>().apply {
        if (arguments != null) {
            value = arguments
        } else {
            value = Drinks(

                name = rating.value?.name ?: "",
                bar = rating.value?.bar ?: "",
                address = rating.value?.address ?: "",
                contents = rating.value?.contents ?: listOf(),
                base = rating.value?.base ?: listOf(),
                category = rating.value?.category ?: "General",
                pairings = rating.value?.pairings?: listOf(),
                body = rating.value?.body ?: -1f,
                sweet = rating.value?.sweet ?: -1f,
                sour = rating.value?.sour ?: -1f,
                main_image = rating.value?.main_photo ?: "",
                images = rating.value?.images ?: listOf(),
                price = rating.value?.price ?: -1
            )
        }
    }

    val drink: LiveData<Drinks>
        get() = _drink

    private val _updatedDrink = MutableLiveData<Drinks>().apply {
        value = Drinks()
    }

    val upDatedDrink: LiveData<Drinks>
        get() = _updatedDrink

    val _bar = MutableLiveData<Bar>().apply {
        value = Bar(
            name = rating.value?.bar ?: "",
            main_image = rating.value?.main_photo ?: "",
            images = rating.value?.images ?: listOf()
        )
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

    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Ratings>()

    val navigateToDetail: LiveData<Ratings>
        get() = _navigateToDetail

    // Handle navigation to Added Success
    private val _navigateToAddedSuccess = MutableLiveData<Ratings>()

    val navigateToAddedSuccess: LiveData<Ratings>
        get() = _navigateToAddedSuccess

    // Handle navigation to Added Fail
    private val _navigateToAddedFail = MutableLiveData<Ratings>()

    val navigateToAddedFail: LiveData<Ratings>
        get() = _navigateToAddedFail

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
        Logger.d("rating.value.author = ${rating.value?.author}")
    }

    fun publish(ratings: Ratings, drinks: Drinks, bar: Bar) {

        coroutineScope.launch {
            Logger.d("fun publish")

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publish(ratings, drinks, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
//                    getRatedDrinks()
                    navigateToAddedSuccess(ratings)
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
        Logger.d("fun add drink")
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
        Logger.d("fun add bar")
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

//    fun bindingDrink() {
//
//        _drink.value?.name = rating.value!!.name
//        _drink.value?.bar = rating.value!!.bar
//        _drink.value?.contents = rating.value!!.contents
//        _drink.value?.base = rating.value!!.base
//        _drink.value?.contents = rating.value!!.contents
//        _drink.value?.category = rating.value!!.category
//        _drink.value?.pairings = rating.value!!.pairings
//        _drink.value?.strong = rating.value!!.strong
//        _drink.value?.sweet = rating.value!!.sweet
//        _drink.value?.sour = rating.value!!.sour
//        _drink.value?.take_again = rating.value!!.take_again
//        _drink.value?.main_image = rating.value!!.main_photo
//        _drink.value?.images = rating.value!!.images!!
//
//        _bar.value?.name = rating.value!!.bar
//        _bar.value?.main_image = rating.value!!.main_photo
//        _bar.value?.images = rating.value!!.images!!
//        Logger.d("drink.value = ${_drink.value?.name}")
//        Logger.d("rating.value = ${rating.value?.name}")
//    }

//    fun getRatedDrinks() {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            val result = drink.value.let {
//                repository.getRatedDrinks(it!!)
//            }
//            Logger.d("repository.getLikedDrinks(it!!)")
//            Logger.d("drink.name = ${drink.value?.name} ")
//            Logger.d("result = $result ")
//
//
//            _updatedDrink.value = when (result) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    result.data
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//                else -> {
//                    _error.value = Mr9Application.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                    null
//                }
//            }
//
//
//        }
//    }

    fun getSearchedRatingDrinksResult(
        searchedText: String,
        searchedBarText: String,
        searchedBarAddressText: String
    ) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = searchedText.let { searchedText ->
                searchedBarText.let { searchedBarText ->
                    searchedBarAddressText.let { searchedBarAddressText ->

                        repository.getSearchedRatingDrinksResult(
                            searchedText,
                            searchedBarText,
                            searchedBarAddressText
                        )
                    }

                }
            }
            Logger.d("searchText=$searchedText  searchBarText=$searchedBarText searchBarAddressText=$searchedBarAddressText")

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
//            _refreshStatus.value = false
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
//            _refreshStatus.value = false
        }
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

    fun onBodyChanged(rating: Float) {
        _rating.value?.body = rating
        Logger.d("_rating.value?.body = ${_rating.value?.body}")
    }

    fun onSourChanged(rating: Float) {
        _rating.value?.sour = rating
        Logger.d("_rating.value?.sour = ${_rating.value?.sour}")
    }

    fun onSweetnessChanged(rating: Float) {
        _rating.value?.sweet = rating
        Logger.d("_rating.value?.acidic = ${_rating.value?.sweet}")
    }

    fun onAddedSuccessNavigated() {
        _navigateToAddedSuccess.value = null
    }

    fun onAddedFailNavigated() {
        _navigateToAddedFail.value = null
    }
    fun navigateToAddedSuccess(ratings: Ratings) {
        _navigateToAddedSuccess.value = ratings
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
        navigateToAddedSuccess(rating.value!!)
    }

    fun navigateToDetail(ratings: Ratings) {
        _navigateToDetail.value = ratings
    }


    var statusAbout = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun setAboutStatus(){
        statusAbout.value = !statusAbout.value!!

    }

    var statusReview = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun setReviewStatus(){

            statusReview.value = true

    }

}