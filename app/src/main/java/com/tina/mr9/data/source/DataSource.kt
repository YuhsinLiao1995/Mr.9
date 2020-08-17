package com.tina.mr9.data.source

import com.tina.mr9.data.*
import com.tina.mr9.data.Result
/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Main entry point for accessing Stylish sources.
 */
interface DataSource{
    suspend fun getDrinks(): Result<List<Drink>>

    suspend fun getRatings(drinkId: String): Result<List<Rating>>

    suspend fun getSearchList(type: String): Result<List<Search>>

    suspend fun getList(searchId: String, column: String): Result<List<Drink>>

    suspend fun getSearchedRatingDrinksResult(searchedText: String,searchedBarText: String, searchedBarAddressText: String): Result<List<Drink>>

    suspend fun getSearchedDrinksResult(searchedText: String): Result<List<Drink>>

    suspend fun getSearchedBarsResult(searchText: String): Result<List<Bar>>

    suspend fun getPairingList(searchId: String, column: String): Result<List<Drink>>

    suspend fun getBarList(searchId: String, column: String): Result<List<Bar>>

    suspend fun getBarDrinksList(searchId: String): Result<List<Drink>>

    suspend fun publish(rating: Rating, drink: Drink, bar: Bar): Result<Boolean>

    suspend fun addDrinks(rating: Rating, drink: Drink, bar: Bar): Result<Boolean>

    suspend fun addBar(rating: Rating, drink: Drink, bar: Bar): Result<Boolean>

    suspend fun getMyRatingDrinks(user: User): Result<List<Rating>>

    suspend fun getRatingResult(followingList : List<String>): Result<List<Rating>>

    suspend fun updateLikedBy(likedStatus: Boolean, user: User, drink: Drink): Result<Boolean>

    suspend fun updateLikedBarBy(likedStatus: Boolean, bar: Bar): Result<Boolean>

    suspend fun updateFollowedBy(likedStatus: Boolean, user: User, searchUser: User): Result<Boolean>

    suspend fun getLikedDrinks(user: User): Result<List<Drink>>

    suspend fun getLikedBar(user: User): Result<List<Bar>>

    suspend fun getUserResult(searchId: String): Result<List<User>>

    suspend fun getFollowUser(followList : List<String>): Result<List<User>>

    suspend fun getAuthorResult(rating: Rating): Result<User>

    suspend fun getMyProfileResult(searchId: String): Result<User>

    suspend fun updateUser(user: User): Result<Boolean>

    suspend fun getRatedDrinks(drink: Drink): Result<Drink>

    suspend fun getTheRatedDrink(rating: Rating): Result<Drink>
}
