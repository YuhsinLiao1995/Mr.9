package com.tina.mr9.data.source.remote

import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.tina.mr9.Mr9Application
import com.tina.mr9.R
import com.tina.mr9.data.*
import com.tina.mr9.data.source.StylishDataSource
import com.tina.mr9.login.UserManager
import com.tina.mr9.util.Logger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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


    override suspend fun getRatings(drinkId: String): Result<List<Ratings>> =
        suspendCoroutine { continuation ->
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

    override suspend fun getSearchList(type: String): Result<List<Search>> =
        suspendCoroutine { continuation ->
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

    override suspend fun getList(searchId: String, column: String): Result<List<Drinks>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("drinks")
                .whereEqualTo(column, searchId)

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

            Log.d("Tina", "List searchId = $searchId")
            Log.d("Tina", "List column = $column")

        }

    override suspend fun getPairingList(searchId: String, column: String): Result<List<Drinks>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("drinks")
                .whereArrayContains(column, searchId)
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

            Log.d("Tina", "Pairing searchId = $searchId")
            Log.d("Tina", "Pairing column = $column")

        }

    override suspend fun getBarList(searchId: String, column: String): Result<List<Bar>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("bar")
                .whereArrayContains(column, searchId)
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

            Log.d("Tina", "Pairing searchId = $searchId")
            Log.d("Tina", "Pairing column = $column")
        }

    override suspend fun getBarDrinksList(searchId: String): Result<List<Drinks>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("drinks")
                .whereEqualTo("bar", searchId)
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
            Log.d("Tina", "BarDrinks searchId = $searchId")
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun publish(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("drinks")
                .whereEqualTo("name", ratings.name)
                .whereEqualTo("bar", ratings.bar)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        if (task.result?.size() == 0) {

                            continuation.resume(Result.DrinkNotExist(ratings))
                        }
//                    val list = mutableListOf<Drinks>()
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val path = document.id

//                        val drinks = document.toObject(Drinks::class.java)
//                        list.add(drinks)
                            val articles = FirebaseFirestore.getInstance().collection("drinks")
                                .document(document.id).collection("rating")
                            val document = articles.document()

                            ratings.id = document.id
                            ratings.createdTime = Calendar.getInstance().timeInMillis

                            document
                                .set(ratings)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Logger.i("Publish: $ratings")

                                        continuation.resume(Result.Success(true))
                                    } else {
                                        task.exception?.let {

                                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                            continuation.resume(Result.Error(it))
                                            return@addOnCompleteListener
                                        }
                                        continuation.resume(
                                            Result.Fail(
                                                Mr9Application.instance.getString(
                                                    R.string.you_know_nothing
                                                )
                                            )
                                        )
                                    }
                                }


                            // calculate avg rating
//                            var amtRating = 0
                            var totalOverallRating = 0f
                            var totalSweet = 0f
                            var totalSour = 0f

                            FirebaseFirestore.getInstance()
                                .collection("drinks").document(path).collection("rating")
                                .get()
                                .addOnCompleteListener() { task ->
                                    for (document in task.result!!) {
                                        Logger.d(document.id + " => " + document.data)

                                        val rating = document.toObject(Ratings::class.java)
                                        Logger.d("rating=$rating")
                                        totalOverallRating += rating.overall_rating
                                        totalSweet += rating.sweet
                                        totalSour += rating.sour
                                    }

                                    val avgOverallRating =
                                        totalOverallRating / task.result!!.size().toFloat()
                                    val avgSweet = totalSweet / task.result!!.size().toFloat()
                                    val avgSour = totalSour / task.result!!.size().toFloat()
                                    Logger.d("rating avg=$avgOverallRating")


                                    FirebaseFirestore.getInstance()
                                        .collection("drinks").document(path)
                                        .update(
                                            "overall_rating",
                                            avgOverallRating,
                                            "sweet",
                                            avgSweet,
                                            "sour",
                                            avgSour,
                                            "amtRating",
                                            task.result!!.size()
                                        )
                                    Logger.d("path = $path")
                                }

                        }


//                    continuation.resume(Result.Success(list))
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun addDrinks(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> =
        suspendCoroutine { continuation ->
            val articles = FirebaseFirestore.getInstance().collection("drinks")
            val document = articles.document()

            drinks.id = document.id
            drinks.createdTime = Calendar.getInstance().timeInMillis

            document
                .set(drinks)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("Add: $drinks")

                        FirebaseFirestore.getInstance()
                            .collection("bar")
                            .whereEqualTo("bar", ratings.bar)
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    if (task.result?.size() == 0) {

                                        continuation.resume(Result.BarNotExist(ratings))
                                    } else {
                                        continuation.resume(Result.Success(true))
                                    }
                                } else {
                                    task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                        continuation.resume(Result.Error(it))
                                        return@addOnCompleteListener
                                    }
                                    continuation.resume(
                                        Result.Fail(
                                            Mr9Application.instance.getString(
                                                R.string.you_know_nothing
                                            )
                                        )
                                    )
                                }


                            }
                    } else {
                        task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun addBar(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> =
        suspendCoroutine { continuation ->
            val articles = FirebaseFirestore.getInstance().collection("bar")
            val document = articles.document()

            bar.id = document.id
            bar.createdTime = Calendar.getInstance().timeInMillis

            document
                .set(bar)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.i("Add: $bar")

                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }

    override suspend fun getMyRatingDrinks(user: User): Result<List<Ratings>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collectionGroup("rating")
                .whereEqualTo("author", user.uid)
                .get()

                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Ratings>()
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val rating = document.toObject(Ratings::class.java)
                            list.add(rating)
                        }
                        continuation.resume(Result.Success(list))
                        Logger.d("task.result.size = ${task.result!!.size()}")
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




    override suspend fun getRatingResult(followingList: List<String>): Result<List<Ratings>> =
        suspendCoroutine { continuation ->

//            var amtFollowing: Int = 0
            val listAll = mutableListOf<Ratings>()

            for ((index, document) in followingList.withIndex()) {

                Logger.i("index=$index")

                FirebaseFirestore.getInstance()
                    .collectionGroup("rating")
                    .whereEqualTo("author", followingList[index])
                    .get()

                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Logger.i("followingList[$index] Success")

                            val list = mutableListOf<Ratings>()

                            for (document in task.result!!) {
                                Logger.d(document.id + " => " + document.data)

                                val rating = document.toObject(Ratings::class.java)

                                Logger.i("rating=$rating")

                                listAll.add(rating)
                            }

//                            listAll.addAll(list)
//                            Logger.d("list.size = ${list.size}")
//                            Logger.d("listAll.size = ${listAll.size}")
//                            Logger.d("getRatingResult task.result.size = ${task.result!!.size()}")


                        } else {
                            task.exception?.let {

                                Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                            continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                        }

//                        Logger.d("listAll end = $listAll")
//                        Logger.d("listAll.size end = ${listAll.size}")


//                        Logger.w("listAll before sort")
//                        listAll.forEach {
//                            Logger.w("$it")
//                        }

                        listAll.sortByDescending {
                            it.createdTime
                        }

                        Logger.w("listAll after sort")
                        listAll.forEach {
                            Logger.v("$it")
                        }
                    }
            }
            continuation.resume(Result.Success(listAll))
        }


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateLikedBy(
        likedStatus: Boolean,
        user: User,
        drinks: Drinks
    ): Result<Boolean> =
        suspendCoroutine { continuation ->

            var newList: List<String> = if (likedStatus) {
                drinks.likedBy.plus(user.uid)
            } else {
                drinks.likedBy.minus(user.uid)
            }

            Logger.d("newlist = $newList")
            FirebaseFirestore.getInstance().collection("drinks")
                .document(drinks.id)
                .update("likedBy", newList)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        for (document in task.result) {
//                            Logger.d(document.id + " => " + document.data)
//
//                            val rating = document.toObject(Drinks::class.java)
//                        }
                        Logger.d("success drinks.id = ${drinks.id}")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(
                            Result.Fail(
                                Mr9Application.instance.getString(
                                    R.string.you_know_nothing
                                )
                            )
                        )
                    }
                }
        }

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateFollowedBy(
        likedStatus: Boolean,
        user: User,
        searchUser: User
    ): Result<Boolean> =
        suspendCoroutine { continuation ->



            var followingList: List<String> = if (likedStatus) {
                UserManager.user.following.plus(searchUser.uid)
//                plus(searchUser.uid)

            } else {
                UserManager.user.following.minus(searchUser.uid)
            }


            var followedByList: List<String> = if (likedStatus) {
                searchUser.followedBy.plus(user.uid)
            } else {
                searchUser.followedBy.minus(user.uid)
            }

            Logger.d("followingList = $followingList")
            FirebaseFirestore.getInstance().collection("users")
                .document(user.uid)
                .update("following", followingList)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        for (document in task.result) {
//                            Logger.d(document.id + " => " + document.data)
//
//                            val rating = document.toObject(Drinks::class.java)
//                        }
                        Logger.d("success user.uid = ${user.uid}")
//                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(
                            Result.Fail(
                                Mr9Application.instance.getString(
                                    R.string.you_know_nothing
                                )
                            )
                        )
                    }


                }


            FirebaseFirestore.getInstance().collection("users")
                .document(searchUser.uid)
                .update("followedBy", followedByList)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
//                        for (document in task.result) {
//                            Logger.d(document.id + " => " + document.data)
//
//                            val rating = document.toObject(Drinks::class.java)
//                        }
                        Logger.d("success searchUser.uid = ${searchUser.uid}")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(
                            Result.Fail(
                                Mr9Application.instance.getString(
                                    R.string.you_know_nothing
                                )
                            )
                        )
                    }
                }

            // Update myProfile to UserManager
            FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("uid",user.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user : User = UserManager.user
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val searchUser = document.toObject(User::class.java)
                            user = searchUser
                        }
                        UserManager.user = user
                        Logger.d("UserManager.user = ${UserManager.user}")
//                        continuation.resume(Result.Success(user))

                        Logger.d("profile task.result.size = ${task.result!!.size()}")
                    } else {
                        task.exception?.let {

                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
                    }
                }



            // Update OthersProfile
//            FirebaseFirestore.getInstance()
//                .collection("users")
//                .whereEqualTo("uid",searchUser.uid)
//                .get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        var user : User = UserManager.user
//                        for (document in task.result!!) {
//                            Logger.d(document.id + " => " + document.data)
//
//                            val searchUser = document.toObject(User::class.java)
//                            user = searchUser
//                        }
//                        UserManager.user = user
//                        Logger.d("UserManager.user = ${UserManager.user}")
////                        continuation.resume(Result.Success(user))
//
//                        Logger.d("profile task.result.size = ${task.result!!.size()}")
//                    } else {
//                        task.exception?.let {
//
//                            Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                            continuation.resume(Result.Error(it))
//                            return@addOnCompleteListener
//                        }
//                        continuation.resume(Result.Fail(Mr9Application.instance.getString(R.string.you_know_nothing)))
//                    }
//                }



        }

    override suspend fun getLikedDrinks(user: User): Result<List<Drinks>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()

            .collection("drinks")
            .whereArrayContains("likedBy",user.uid)
            .get()

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Drinks>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val drink = document.toObject(Drinks::class.java)
                        list.add(drink)
                        Logger.d("task.result.size() = ${task.result!!.size()}")
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


    override suspend fun getUserResult(searchId: String): Result<List<User>> =
        suspendCoroutine { continuation ->

            Logger.d("remotedatasource")
            FirebaseFirestore.getInstance()
                .collection("users")
                .orderBy("email")
                .startAt(searchId)
                .endAt(searchId + "\uf8ff")
                .get()

                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<User>()
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val searchUser = document.toObject(User::class.java)
                            list.add(searchUser)
                        }
                        continuation.resume(Result.Success(list))
                        Logger.d("task.result.size = ${task.result!!.size()}")
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


    override suspend fun getMyProfileResult(searchId: String): Result<User> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("uid",searchId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var user : User = UserManager.user
                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)

                            val searchUser = document.toObject(User::class.java)
                            user = searchUser
                        }
                        continuation.resume(Result.Success(user))

                        Logger.d("profile task.result.size = ${task.result!!.size()}")
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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun updateUser(user: User): Result<Boolean> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection("users")
                .whereEqualTo("uid", user.uid)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Logger.d("task.isSuccessful")

                        for (document in task.result!!) {
                            Logger.d(document.id + " => " + document.data)


                        }

                        Logger.d("task.result.size = ${task.result!!.size()}")

                        if (task.result!!.size() == 0) {

                            Logger.d("add new User")
                            val articles = FirebaseFirestore.getInstance().collection("users")
                            val document = articles.document(user.uid)

                            Logger.d("val document = articles.document()")

                            user.createdTime = Calendar.getInstance().timeInMillis

                            Logger.d("user.createdTime = ${user.createdTime}")

                            document
                                .set(user)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Logger.i("Add: $user")

                                        continuation.resume(Result.Success(true))
                                    } else {

                                        Logger.d("else")

                                        task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                            continuation.resume(Result.Error(it))
                                            return@addOnCompleteListener


                                        }
                                        continuation.resume(
                                            Result.Fail(
                                                Mr9Application.instance.getString(
                                                    R.string.you_know_nothing
                                                )
                                            )
                                        )
                                    }
                                }

                            Logger.d("not complete")


                        } else {

                            Logger.d("Update User")


                            val articles = FirebaseFirestore.getInstance().collection("users")
                            val document = articles.document(user.uid)

                            Logger.d("val document = articles.document()")


                            Logger.d("user.createdTime = ${user.createdTime}")

                            document
                                .update(
                                    "name",
                                    user.name,
                                    "email",
                                    user.email,
                                    "image",
                                    user.image
                                )
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Logger.i("Update: $user")

                                        continuation.resume(Result.Success(true))
                                    } else {
                                        task.exception?.let {

//                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                                            continuation.resume(Result.Error(it))
                                            return@addOnCompleteListener

                                            Logger.d("else")
                                        }
                                        continuation.resume(
                                            Result.Fail(
                                                Mr9Application.instance.getString(
                                                    R.string.you_know_nothing
                                                )
                                            )
                                        )
                                    }
                                }
                        }

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


    override suspend fun getRatedDrinks(drinks: Drinks): Result<Drinks> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()

            .collection("drinks")
            .whereEqualTo("name",drinks.name)
            .whereEqualTo("bar", drinks.bar)
            .whereEqualTo("address",drinks.address)
            .get()

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var drink : Drinks = drinks
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        drink = document.toObject(Drinks::class.java)

                        Logger.d("task.result.size() = ${task.result!!.size()}")
                    }
                    continuation.resume(Result.Success(drink))
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





