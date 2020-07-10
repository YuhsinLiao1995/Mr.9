package com.tina.mr9.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.tina.mr9.Mr9Application
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.tina.mr9.data.Result

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [HomeFragment].
 */
class HomeViewModel(private val repository: StylishRepository) : ViewModel() {

    private val _drink = MutableLiveData<List<Drinks>>()

    val drinks: LiveData<List<Drinks>>
        get() = _drink

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

//        getAllData()
        getDrinksResult()
    }

//    fun getAllData() {
////        var articlesFirebase = db.collection("drinks")
////            .orderBy("createdTime", Query.Direction.DESCENDING)
//        drinksFirebase
//            .get()
//            .addOnSuccessListener { result ->
//
//                Log.d("Tina", "${result.documents}")
//
//                for (document in result) {
//                    Log.d("Tina", "${document.id} => ${document.data}")
//
//
//                    val drink = Drinks(
//                        name = document.data["name"] as String,
//                        bar = document.data["bar"] as String,
//                        base = document.data["base"] as ArrayList<String>,
//                        contents = document.data["contents"] as ArrayList<String>,
//                        pairings = document.data["pairings"] as ArrayList<String>,
//                        category = document.data["category"] as String,
//                        main_image = document.data["main_image"] as String,
//                        images = document.data["images"] as ArrayList<String?>,
//                        tag = document.data["tag"] as ArrayList<String>
////                        rating = document.data["rating"] as HashMap<Ratings, Any>
//                    )
//
//                    Log.d("Tina", "drinks => $drinks")
//
//                    drinksList.add(drink)
//
//                    drinks.value = drinksList
//
//                    Log.d("Tina", "articles => ${drinks.value}")
//
////                    val ratings = document.collection("rating").get
//                }
//
//            }
//            .addOnFailureListener { exception ->
//                Log.d("Tina", "Error getting documents: ", exception)
//            }
//
//    }

    fun getDrinksResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getDrinks()

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

    fun addDate() {


        val drinks = FirebaseFirestore.getInstance()
            .collection("drinks")

        val documents = drinks.document()

        val data = hashMapOf(
            "name" to "SILVER FUZZ",
            "base" to arrayListOf("GIN","RUM"),
            "bar" to "draft land",
            "category" to "unknown",
            "contents" to arrayListOf("Honey","Lemon"),
            "pairings" to arrayListOf("donuts"),
            "tag" to arrayListOf("東區"),
            "main_image" to "https://s3-ap-northeast-1.amazonaws.com/oneday-wordpress/wp-content/uploads/2018/11/02080104/DSC05517.jpg"
        )
        documents.set(data)

//        db.collection("post")
//            .add(data)
//            .addOnSuccessListener { documentReference ->
//
//                Log.d(
//                    "Tina",
//                    "DocumentSnapshot added with ID: " + documentReference.id
//                )
//                    }
//
//            .addOnFailureListener { e -> Log.w("Tina", "Error adding document", e) }


    }

    fun getDateTime(s: Number): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun navigateToDetail(drinks: Drinks) {
        _navigateToDetail.value = drinks
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getDrinksResult()
        }
    }


}