package com.tina.mr9.data.source

import com.tina.mr9.data.*
import com.tina.mr9.data.Result
/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Main entry point for accessing Stylish sources.
 */
interface StylishDataSource{
    suspend fun getDrinks(): Result<List<Drinks>>

    suspend fun getRatings(drinkId: String): Result<List<Ratings>>

    suspend fun getSearchList(type: String): Result<List<Search>>

    suspend fun getList(searchId: String, column: String): Result<List<Drinks>>

    suspend fun getPairingList(searchId: String, column: String): Result<List<Drinks>>

    suspend fun getBarList(searchId: String, column: String): Result<List<Bar>>

    suspend fun getBarDrinksList(searchId: String): Result<List<Drinks>>

    suspend fun publish(ratings: Ratings,drinks: Drinks, bar: Bar): Result<Boolean>

    suspend fun addDrinks(ratings: Ratings,drinks: Drinks, bar: Bar): Result<Boolean>

    suspend fun addBar(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean>

    suspend fun getMyRatingDrinks(user: User): Result<List<Ratings>>

}
