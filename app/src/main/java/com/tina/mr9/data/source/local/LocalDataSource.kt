package com.tina.mr9.data.source.local

import android.content.Context
import com.tina.mr9.data.*
import com.tina.mr9.data.source.DataSource

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Concrete implementation of a Stylish source as a db.
 */
class LocalDataSource(val context: Context) : DataSource {
    override suspend fun getDrinks(): Result<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRatings(drinkId:String): Result<List<Rating>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchList(type: String): Result<List<Search>> {
        TODO("Not yet implemented")
    }

    override suspend fun getList(searchId: String, column: String): Result<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchedRatingDrinksResult(searchedText: String,searchedBarText: String, searchedBarAddressText: String): Result<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchedDrinksResult(searchedText: String): Result<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchedBarsResult(searchText: String): Result<List<Bar>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPairingList(searchId: String, column: String): Result<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBarList(searchId: String, column: String): Result<List<Bar>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBarDrinksList(searchId: String): Result<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun publish(rating: Rating, drink: Drink, bar: Bar): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addDrinks(rating: Rating, drink: Drink, bar: Bar): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addBar(rating: Rating, drink: Drink, bar: Bar): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyRatingDrinks(user: User): Result<List<Rating>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRatingResult(followingList: List<String>): Result<List<Rating>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateLikedBy(likedStatus: Boolean, user: User, drink: Drink): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateLikedBarBy(likedStatus: Boolean, bar: Bar): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun updateFollowedBy(likedStatus: Boolean, user: User, searchUser: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getLikedDrinks(user: User): Result<List<Drink>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLikedBar(user: User): Result<List<Bar>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserResult(searchId: String): Result<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFollowUser(followList: List<String>): Result<List<User>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAuthorResult(rating: Rating): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getMyProfileResult(searchId: String): Result<User> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getRatedDrinks(drink: Drink): Result<Drink> {
        TODO("Not yet implemented")
    }

    override suspend fun getTheRatedDrink(rating: Rating): Result<Drink> {
        TODO("Not yet implemented")
    }


}
