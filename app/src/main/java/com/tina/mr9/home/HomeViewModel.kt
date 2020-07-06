package com.tina.mr9.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.network.LoadApiStatus
import com.tina.mr9.util.Util.getString
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [HomeFragment].
 */
class HomeViewModel(private val stylishRepository: StylishRepository) : ViewModel() {

    var drinks = MutableLiveData<MutableList<Drinks>>()

    val drinksList = mutableListOf<Drinks>()

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var drinksFirebase = db.collection("drinks")

    var ratingsFirebase = db.collection("drinks")


        private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getAllData()
//        addDate()
    }

    fun getAllData() {
//        var articlesFirebase = db.collection("drinks")
//            .orderBy("createdTime", Query.Direction.DESCENDING)
        drinksFirebase
            .get()
            .addOnSuccessListener { result ->

                Log.d("Tina", "${result.documents}")

                for (document in result) {
                    Log.d("Tina", "${document.id} => ${document.data}")


                    val drink = Drinks(
                        name = document.data["name"] as String,
                        bar = document.data["bar"] as String,
                        base = document.data["base"] as ArrayList<String>,
                        contents = document.data["contents"] as ArrayList<String>,
                        pairings = document.data["pairings"] as ArrayList<String>,
                        category = document.data["category"] as String,
                        main_image = document.data["main_image"] as String,
                        tag = document.data["tag"] as ArrayList<String>
//                        rating = document.data["rating"] as HashMap<Ratings, Any>
                    )

                    Log.d("Tina", "drinks => $drinks")

                    drinksList.add(drink)

                    drinks.value = drinksList

                    Log.d("Tina", "articles => ${drinks.value}")

//                    val ratings = document.collection("rating").get
                }

            }
            .addOnFailureListener { exception ->
                Log.d("Tina", "Error getting documents: ", exception)
            }

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


}