package com.tina.mr9.data.source.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.data.source.StylishDataSource
import com.tina.mr9.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.tina.mr9.network.StylishApi
import com.tina.mr9.util.Util.isInternetConnected

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Implementation of the Stylish source that from network.
 */
object StylishRemoteDataSource : StylishDataSource {

    override suspend fun getDrinks(): Result<List<Drinks>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("drinks")
//            .collectionGroup("rating")
//            .document("YqgjBwOGFtsoR9VagMPx")
//            .collection("rating")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Drinks>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val drink = document.toObject(Drinks::class.java)
                        list.add(drink)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                }
            }


    }


    override suspend fun getRatings(drinkId: String): Result<List<Ratings>>  = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("drinks")
//            .collectionGroup("rating")
            .document(drinkId)
            .collection("rating")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Ratings>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val ratings = document.toObject(Ratings::class.java)
                        list.add(ratings)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                }
            }


    }

    override suspend fun getSearchList(type: String): Result<List<Search>>  = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(type)
//            .collectionGroup("rating")
//            .document("YqgjBwOGFtsoR9VagMPx")
//            .collection("rating")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Search>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val search = document.toObject(Search::class.java)
                        list.add(search)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                }
            }

    }

    override suspend fun getList(searchId: String, column: String): Result<List<Drinks>>  = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("drinks")
            .whereEqualTo(column,searchId)

//            .collectionGroup("rating")
//            .document("YqgjBwOGFtsoR9VagMPx")
//            .collection("rating")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Drinks>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val drink = document.toObject(Drinks::class.java)
                        list.add(drink)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                }
            }

        Log.d("Tina","List searchId = $searchId")
        Log.d("Tina","List column = $column")

    }

    override suspend fun getPairingList(searchId: String, column: String): Result<List<Drinks>>  = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("drinks")
            .whereArrayContains(column,searchId)
            .get()

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Drinks>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val drink = document.toObject(Drinks::class.java)
                        list.add(drink)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                }
            }

        Log.d("Tina","Pairing searchId = $searchId")
        Log.d("Tina","Pairing column = $column")

    }

    override suspend fun getBarList(searchId: String, column: String): Result<List<Bar>>  = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("bar")
            .whereArrayContains(column,searchId)
            .get()

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Bar>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val bar = document.toObject(Bar::class.java)
                        list.add(bar)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                }
            }

        Log.d("Tina","Pairing searchId = $searchId")
        Log.d("Tina","Pairing column = $column")
    }

    override suspend fun getBarDrinksList(searchId: String): Result<List<Drinks>>  = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("drinks")
            .whereEqualTo("bar",searchId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Drinks>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val drinks = document.toObject(Drinks::class.java)
                        list.add(drinks)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                }
            }
        Log.d("Tina","BarDrinks searchId = $searchId")
    }
}
