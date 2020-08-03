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

    suspend fun getSearchedRatingDrinksResult(searchedText: String,searchedBarText: String, searchedBarAddressText: String): Result<List<Drinks>>

    suspend fun getSearchedDrinksResult(searchedText: String): Result<List<Drinks>>

    suspend fun getSearchedBarsResult(searchText: String): Result<List<Bar>>

    suspend fun getPairingList(searchId: String, column: String): Result<List<Drinks>>

    suspend fun getBarList(searchId: String, column: String): Result<List<Bar>>

    suspend fun getBarDrinksList(searchId: String): Result<List<Drinks>>

    suspend fun publish(ratings: Ratings,drinks: Drinks, bar: Bar): Result<Boolean>

    suspend fun addDrinks(ratings: Ratings,drinks: Drinks, bar: Bar): Result<Boolean>

    suspend fun addBar(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean>

    suspend fun getMyRatingDrinks(user: User): Result<List<Ratings>>

    suspend fun getRatingResult(followingList : List<String>): Result<List<Ratings>>

    suspend fun updateLikedBy(likedStatus: Boolean, user: User, drinks: Drinks): Result<Boolean>

    suspend fun updateLikedBarBy(likedStatus: Boolean, bar: Bar): Result<Boolean>

    suspend fun updateFollowedBy(likedStatus: Boolean, user: User, searchUser: User): Result<Boolean>

    suspend fun getLikedDrinks(user: User): Result<List<Drinks>>

    suspend fun getLikedBar(user: User): Result<List<Bar>>

    suspend fun getUserResult(searchId: String): Result<List<User>>

    suspend fun getFollowUser(followList : List<String>): Result<List<User>>

    suspend fun getAuthorResult(ratings: Ratings): Result<User>

    suspend fun getMyProfileResult(searchId: String): Result<User>

    suspend fun updateUser(user: User): Result<Boolean>

    suspend fun getRatedDrinks(drinks: Drinks): Result<Drinks>

    suspend fun getTheRatedDrink(ratings: Ratings): Result<Drinks>
}
