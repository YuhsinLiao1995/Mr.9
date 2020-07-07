package com.tina.mr9.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.source.StylishDataSource
import com.tina.mr9.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.tina.mr9.data.Result

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Implementation of the Stylish source that from network.
 */
object StylishRemoteDataSource : StylishDataSource {

    override suspend fun getDrinks(): Result<List<Drinks>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection("drinks")
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Drinks>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val article = document.toObject(Drinks::class.java)
                        list.add(article)
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

}
