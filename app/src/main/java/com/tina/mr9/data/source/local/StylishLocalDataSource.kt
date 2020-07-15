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

    override suspend fun publish(ratings: Ratings): Result<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
