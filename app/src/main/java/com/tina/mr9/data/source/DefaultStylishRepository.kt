package com.tina.mr9.data.source

import com.tina.mr9.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Concrete implementation to load Stylish sources.
 */
class DefaultStylishRepository(private val remoteDataSource: StylishDataSource,
                               private val stylishLocalDataSource: StylishDataSource,
                               private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StylishRepository {
    override suspend fun getDrinks(): Result<List<Drinks>> {
        return remoteDataSource.getDrinks()
    }

    override suspend fun getRatings(drinkId:String): Result<List<Ratings>> {
        return remoteDataSource.getRatings(drinkId)
    }

    override suspend fun getSearchList(type: String): Result<List<Search>> {
        return remoteDataSource.getSearchList(type = type)
    }

    override suspend fun getSearchedRatingDrinksResult(searchedText: String,searchedBarText: String, searchedBarAddressText: String): Result<List<Drinks>> {
        return remoteDataSource.getSearchedRatingDrinksResult(searchedText = searchedText, searchedBarText = searchedBarText, searchedBarAddressText = searchedBarAddressText)
    }

    override suspend fun getSearchedDrinksResult(searchedText: String): Result<List<Drinks>> {
        return remoteDataSource.getSearchedDrinksResult(searchedText = searchedText)
    }

    override suspend fun getSearchedBarsResult(searchText: String): Result<List<Bar>> {
        return remoteDataSource.getSearchedBarsResult(searchText = searchText)
    }

    override suspend fun getList(searchId: String, column: String): Result<List<Drinks>> {
        return remoteDataSource.getList(searchId = searchId, column = column)
    }

    override suspend fun getPairingList(searchId: String, column: String): Result<List<Drinks>> {
        return remoteDataSource.getPairingList(searchId = searchId, column = column)
    }

    override suspend fun getBarList(searchId: String, column: String): Result<List<Bar>> {
        return remoteDataSource.getBarList(searchId = searchId, column = column)
    }

    override suspend fun getBarDrinks(searchId: String): Result<List<Drinks>> {
        return remoteDataSource.getBarDrinksList(searchId = searchId)
    }

    override suspend fun publish(ratings: Ratings,drinks: Drinks, bar: Bar): Result<Boolean> {
        return remoteDataSource.publish(ratings = ratings, drinks = drinks, bar = bar)
    }

    override suspend fun addDrinks(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> {
        return remoteDataSource.addDrinks(ratings = ratings,drinks = drinks, bar = bar)
    }

    override suspend fun addBar(ratings: Ratings, drinks: Drinks, bar: Bar): Result<Boolean> {
        return remoteDataSource.addBar(ratings = ratings,drinks = drinks, bar = bar)
    }

    override suspend fun getMyRatingDrinks(user: User): Result<List<Ratings>> {
        return remoteDataSource.getMyRatingDrinks(user = user)
    }

    override suspend fun getRatingResult(followingList: List<String>): Result<List<Ratings>> {
        return remoteDataSource.getRatingResult(followingList = followingList)
    }

    override suspend fun updateLikedBy(likedStatus: Boolean, user:User,drinks: Drinks
    ): Result<Boolean> {
        return remoteDataSource.updateLikedBy(likedStatus = likedStatus, user = user, drinks =drinks)
    }

    override suspend fun updateLikedBarBy(likedStatus: Boolean, bar: Bar): Result<Boolean> {
        return remoteDataSource.updateLikedBarBy(likedStatus = likedStatus, bar = bar)
    }

    override suspend fun updateFollowedBy(likedStatus: Boolean, user: User, searchUser: User
    ): Result<Boolean> {
        return remoteDataSource.updateFollowedBy(likedStatus = likedStatus, user = user, searchUser = searchUser)
    }

    override suspend fun getLikedDrinks(user: User): Result<List<Drinks>> {
        return remoteDataSource.getLikedDrinks(user = user)
    }

    override suspend fun getLikedBar(user: User): Result<List<Bar>> {
        return remoteDataSource.getLikedBar(user = user)
    }

    override suspend fun getUserResult(searchId: String): Result<List<User>> {
        return remoteDataSource.getUserResult(searchId = searchId)
    }

    override suspend fun getFollowUser(followList: List<String>): Result<List<User>> {
        return remoteDataSource.getFollowUser(followList = followList)
    }

    override suspend fun getAuthorResult(ratings: Ratings): Result<User> {
        return remoteDataSource.getAuthorResult(ratings = ratings)
    }

    override suspend fun getMyProfileResult(searchId: String): Result<User> {
        return remoteDataSource.getMyProfileResult(searchId = searchId)
    }

    override suspend fun updateUser(user: User): Result<Boolean> {
        return remoteDataSource.updateUser(user = user)
    }

    override suspend fun getRatedDrinks(drinks: Drinks): Result<Drinks> {
        return remoteDataSource.getRatedDrinks(drinks = drinks)
    }

    override suspend fun getTheRatedDrink(ratings: Ratings): Result<Drinks> {
        return remoteDataSource.getTheRatedDrink(ratings = ratings)
    }


}
