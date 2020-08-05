package com.tina.mr9.data.source

import com.tina.mr9.data.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Yuhsin Liao in Jul. 2020.
 *
 * Concrete implementation to load Stylish sources.
 */
class DefaultRepository(private val remoteDataSource: DataSource,
                        private val localDataSource: DataSource,
                        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {
    override suspend fun getDrinks(): Result<List<Drink>> {
        return remoteDataSource.getDrinks()
    }

    override suspend fun getRatings(drinkId:String): Result<List<Rating>> {
        return remoteDataSource.getRatings(drinkId)
    }

    override suspend fun getSearchList(type: String): Result<List<Search>> {
        return remoteDataSource.getSearchList(type = type)
    }

    override suspend fun getSearchedRatingDrinksResult(searchedText: String,searchedBarText: String, searchedBarAddressText: String): Result<List<Drink>> {
        return remoteDataSource.getSearchedRatingDrinksResult(searchedText = searchedText, searchedBarText = searchedBarText, searchedBarAddressText = searchedBarAddressText)
    }

    override suspend fun getSearchedDrinksResult(searchedText: String): Result<List<Drink>> {
        return remoteDataSource.getSearchedDrinksResult(searchedText = searchedText)
    }

    override suspend fun getSearchedBarsResult(searchText: String): Result<List<Bar>> {
        return remoteDataSource.getSearchedBarsResult(searchText = searchText)
    }

    override suspend fun getList(searchId: String, column: String): Result<List<Drink>> {
        return remoteDataSource.getList(searchId = searchId, column = column)
    }

    override suspend fun getPairingList(searchId: String, column: String): Result<List<Drink>> {
        return remoteDataSource.getPairingList(searchId = searchId, column = column)
    }

    override suspend fun getBarList(searchId: String, column: String): Result<List<Bar>> {
        return remoteDataSource.getBarList(searchId = searchId, column = column)
    }

    override suspend fun getBarDrinks(searchId: String): Result<List<Drink>> {
        return remoteDataSource.getBarDrinksList(searchId = searchId)
    }

    override suspend fun publish(rating: Rating, drink: Drink, bar: Bar): Result<Boolean> {
        return remoteDataSource.publish(rating = rating, drink = drink, bar = bar)
    }

    override suspend fun addDrinks(rating: Rating, drink: Drink, bar: Bar): Result<Boolean> {
        return remoteDataSource.addDrinks(rating = rating,drink = drink, bar = bar)
    }

    override suspend fun addBar(rating: Rating, drink: Drink, bar: Bar): Result<Boolean> {
        return remoteDataSource.addBar(rating = rating,drink = drink, bar = bar)
    }

    override suspend fun getMyRatingDrinks(user: User): Result<List<Rating>> {
        return remoteDataSource.getMyRatingDrinks(user = user)
    }

    override suspend fun getRatingResult(followingList: List<String>): Result<List<Rating>> {
        return remoteDataSource.getRatingResult(followingList = followingList)
    }

    override suspend fun updateLikedBy(likedStatus: Boolean, user:User, drink: Drink
    ): Result<Boolean> {
        return remoteDataSource.updateLikedBy(likedStatus = likedStatus, user = user, drink =drink)
    }

    override suspend fun updateLikedBarBy(likedStatus: Boolean, bar: Bar): Result<Boolean> {
        return remoteDataSource.updateLikedBarBy(likedStatus = likedStatus, bar = bar)
    }

    override suspend fun updateFollowedBy(likedStatus: Boolean, user: User, searchUser: User
    ): Result<Boolean> {
        return remoteDataSource.updateFollowedBy(likedStatus = likedStatus, user = user, searchUser = searchUser)
    }

    override suspend fun getLikedDrinks(user: User): Result<List<Drink>> {
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

    override suspend fun getAuthorResult(rating: Rating): Result<User> {
        return remoteDataSource.getAuthorResult(rating = rating)
    }

    override suspend fun getMyProfileResult(searchId: String): Result<User> {
        return remoteDataSource.getMyProfileResult(searchId = searchId)
    }

    override suspend fun updateUser(user: User): Result<Boolean> {
        return remoteDataSource.updateUser(user = user)
    }

    override suspend fun getRatedDrinks(drink: Drink): Result<Drink> {
        return remoteDataSource.getRatedDrinks(drink = drink)
    }

    override suspend fun getTheRatedDrink(rating: Rating): Result<Drink> {
        return remoteDataSource.getTheRatedDrink(rating = rating)
    }


}
