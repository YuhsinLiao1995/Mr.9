package com.tina.mr9.data.source

import com.tina.mr9.data.Result
import com.tina.mr9.data.*

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Interface to the Stylish layers.
 */
interface StylishRepository {

    suspend fun getDrinks(): Result<List<Drinks>>

    suspend fun getRatings(drinkId: String): Result<List<Ratings>>
}
