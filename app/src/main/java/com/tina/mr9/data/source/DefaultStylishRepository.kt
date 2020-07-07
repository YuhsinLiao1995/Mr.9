package com.tina.mr9.data.source

import androidx.lifecycle.LiveData
import com.tina.mr9.data.*
import com.tina.mr9.data.source.StylishDataSource
import com.tina.mr9.data.source.StylishRepository
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


}
