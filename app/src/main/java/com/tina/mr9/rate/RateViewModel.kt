package com.tina.mr9.rate

import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.storage.FirebaseStorage
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Bar
import com.tina.mr9.data.Drink
import com.tina.mr9.data.Rating
import com.tina.mr9.data.Result
import com.tina.mr9.data.source.Repository
import com.tina.mr9.login.UserManager
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
 * The [ViewModel] that is attached to the [RateFragment].
 */
class RateViewModel(
    private val repository: Repository,
    private val arguments: Drink?
) : ViewModel() {

    val _rating = MutableLiveData<Rating>().apply {

            value = Rating(
                author = UserManager.user.uid,
                authorName = UserManager.user.name,
                authorImage = UserManager.user.image,
                sour = 0f,
                sweet = 0f,
                body = 0f,
                overall_rating = 0f


            )

        arguments?.let {
            value = Rating(
                name = arguments.name,
                bar = arguments.bar,
                contents = arguments.contents,
                base = arguments.base,
                category = arguments.category,
                author = UserManager.user.uid,
                authorName = UserManager.user.name,
                authorImage = UserManager.user.image,
                sour = 0f,
                sweet = 0f,
                body = 0f,
                overall_rating = 0f
            )


        }

    }

    private val _searchedDrinks = MutableLiveData<List<Drink>>()

    val searchedDrink: LiveData<List<Drink>>
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


    val rating: LiveData<Rating>
        get() = _rating

    val _drink = MutableLiveData<Drink>().apply {
        if (arguments != null) {
            value = arguments
        } else {
            value = Drink(

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
                price = rating.value?.price ?: -1,
                overall_rating = rating.value?.overall_rating ?: -1f
            )
        }
    }

    val drink: LiveData<Drink>
        get() = _drink

    private val _updatedDrink = MutableLiveData<Drink>().apply {
        value = Drink()
    }

    val upDatedDrink: LiveData<Drink>
        get() = _updatedDrink

    private val _bar = MutableLiveData<Bar>().apply {
        value = Bar(
            name = rating.value?.bar ?: "",
            main_image = rating.value?.main_photo ?: "",
            images = rating.value?.images ?: listOf(),
            overallRating = rating.value?.overall_rating ?: -1f
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
    private val _navigateToDetail = MutableLiveData<Rating>()

    val navigateToDetail: LiveData<Rating>
        get() = _navigateToDetail

    // Handle navigation to Added Success
    private val _navigateToAddedSuccess = MutableLiveData<Rating>()

    val navigateToAddedSuccess: LiveData<Rating>
        get() = _navigateToAddedSuccess

    // Handle navigation to Added Fail
    private val _navigateToAddedFail = MutableLiveData<Rating>()

    val navigateToAddedFail: LiveData<Rating>
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

    fun bindData(){
        drink.value?.name = rating.value?.name ?: ""
        drink.value?.bar = rating.value?.bar ?: ""
        drink.value?.address = rating.value?.address ?: ""
        drink.value?.contents = rating.value?.contents ?: listOf()
        drink.value?.base = rating.value?.base ?: listOf()
        drink.value?.category = rating.value?.category ?: "General"
        drink.value?.pairings = rating.value?.pairings?: listOf()
        drink.value?.body = rating.value?.body ?: -1f
        drink.value?.sweet = rating.value?.sweet ?: -1f
        drink.value?.sour = rating.value?.sour ?: -1f
        drink.value?.main_image = rating.value?.main_photo ?: ""
        drink.value?.images = rating.value?.images ?: listOf()
        drink.value?.price = rating.value?.price ?: -1
        drink.value?.overall_rating = rating.value?.overall_rating ?: -1f

        bar.value?.name = rating.value?.bar ?: ""
        bar.value?.main_image = rating.value?.main_photo ?: ""
        bar.value?.images = rating.value?.images ?: listOf()
        bar.value?.overallRating = rating.value?.overall_rating ?: -1f

        publish(rating.value ?: Rating(),drink.value ?: Drink(),bar.value ?: Bar())
    }

    fun publish(rating: Rating, drink: Drink, bar: Bar) {

        coroutineScope.launch {
            Logger.d("fun publish")

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publish(rating, drink, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    navigateToAddedSuccess(rating)
                }
                is Result.DrinkNotExist -> {
                    publish2(rating, drink, bar)
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

    private fun publish2(rating: Rating, drink: Drink, bar: Bar) {
        Log.d("Tina", "ifcalled")
        Logger.d("fun add drink")
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addDrinks(rating, drink, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    publish(rating, drink, bar)
                    leave(true)
                }
                is Result.BarNotExist -> {
                    addBar(rating, drink, bar)
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

    private fun addBar(rating: Rating, drink: Drink, bar: Bar) {
        Logger.d("fun add bar")
        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addBar(rating, drink, bar)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    publish(rating, drink, bar)
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

    fun chipFun(taglist: MutableList<String>, chipGroup: ChipGroup, newChip: EditText, newTag:  MutableLiveData<String>, paddingDp: Int) {

        for (index in taglist.indices) {
            val tagName = taglist[index]
            val chip = Chip(chipGroup.context)

            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
            chip.text = tagName
            chip.textSize = 14f
            chip.setTextColor(Color.WHITE)
            val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
            val chipColors = intArrayOf(Color.parseColor("#3f3a3a"))
            val chipColorsStateList = ColorStateList(states, chipColors)
            chip.chipBackgroundColor = chipColorsStateList
            chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))

            chip.setOnClickListener {
                chip.isCloseIconEnabled = !chip.isCloseIconEnabled
                chip.setOnCloseIconClickListener {
                    chipGroup.removeView(chip)
                    Logger.d("$taglist.toString()")
                    taglist.remove(tagName)
                }
            }
            chip.text = newChip.text
            chipGroup.addView(chip)
        }
        newTag.value = null
    }

    fun updateChip(taglist: MutableList<String>,chipGroup: ChipGroup,paddingDp: Int) {
        for (index in taglist.indices) {
            val tagName = taglist[index]
            val chip = Chip(chipGroup.context)

            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp)
            chip.text = tagName
            chip.textSize = 14f
            chip.setTextColor(Color.WHITE)
            val states = arrayOf(intArrayOf(-android.R.attr.state_checked))
            val chipColors = intArrayOf(Color.parseColor("#999999"))
            val chipColorsStateList = ColorStateList(states, chipColors)
            chip.chipBackgroundColor = chipColorsStateList
            chip.closeIconTint = ColorStateList(states, intArrayOf(Color.WHITE))

            chip.setOnClickListener {
                chip.isCloseIconEnabled = !chip.isCloseIconEnabled
                //Added click listener on close icon to remove tag from ChipGroup
                chip.setOnCloseIconClickListener {
                    taglist.remove(tagName)
                    chipGroup.removeView(chip)
                    Logger.d("$taglist.toString()")
                    taglist.remove(tagName)
                }
            }

            taglist.plus(taglist)
            Logger.d("vtaglist = $taglist")

            chipGroup.addView(chip)
        }
    }

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
    private fun navigateToAddedSuccess(rating: Rating) {
        _navigateToAddedSuccess.value = rating
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
        navigateToAddedSuccess(rating.value!!)
    }

    fun navigateToDetail(rating: Rating) {
        _navigateToDetail.value = rating
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

    fun setReviewStatus() {
        statusReview.value = true
    }

    fun uploadImage(saveUri: Uri) {
        var firstPhoto = true
        val filename = UUID.randomUUID().toString()
        val image = MutableLiveData<String>()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        Log.d("TIna", "saveUri = $saveUri")

        ref.putFile(saveUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Tina", "it = $it")
                    image.value = it.toString()
                    if (firstPhoto) {
                        rating.value?.main_photo = image.value!!
                        rating.value?.images =
                            listOf(listOf(image.value).toString())
                        firstPhoto = false


                    } else {
                        rating.value?.images =
                            listOf(image.toString())
                        Log.d("Tina", "not first photo")
                    }
                    Log.d(
                        "Tina",
                        "mainImage = ${rating.value?.main_photo}; images = ${rating.value?.images}"
                    )
                    images.value?.add(it.toString())
                    images.value = images.value
                    rating.value?.images = images.value

                }
            }
    }

}