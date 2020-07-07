package com.tina.mr9.detailpage

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.data.*
import com.tina.mr9.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * The [ViewModel] that is attached to the [DetailFragment].
 */
class DetailViewModel(private val stylishRepository: StylishRepository) : ViewModel() {

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