package com.tina.mr9.data.source.local

import android.content.Context
import com.tina.mr9.data.*
import com.tina.mr9.data.source.StylishDataSource

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation of a Stylish source as a db.
 */
class StylishLocalDataSource(val context: Context) : StylishDataSource {
    override suspend fun getDrinks(): Result<List<Drinks>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRatings(drinkId:String): Result<List<Ratings>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchList(type: String): Result<List<Search>> {
        TODO("Not yet implemented")
    }

    override suspend fun getList(searchId: String, column: String): Result<List<Drinks>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPairingList(searchId: String, column: String): Result<List<Drinks>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBarList(searchId: String, column: String): Result<List<Bar>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBarDrinksList(searchId: String): Result<List<Drinks>> {
        TODO("Not yet implemented")
    }

    override suspend fun publish(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addDrinks(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addBar(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyRatingDrinks(user: User): Result<List<Ratings>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateLikedBy(likedStatus: Boolean, user: User, drinks: Drinks): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getLikedDrinks(user: User): Result<List<Drinks>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserResult(searchId: String): Result<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }


}
