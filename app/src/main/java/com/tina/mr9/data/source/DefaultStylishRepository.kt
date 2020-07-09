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


}
