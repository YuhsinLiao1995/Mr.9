package com.tina.mr9.data.source.local

import android.content.Context
import com.tina.mr9.data.Drinks
import com.tina.mr9.data.Result
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


}
