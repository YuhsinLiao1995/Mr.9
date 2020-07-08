package com.tina.mr9.data.source

import androidx.lifecycle.LiveData
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


}
