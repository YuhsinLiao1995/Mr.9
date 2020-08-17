package com.tina.mr9.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.tina.mr9.data.source.DefaultRepository
import com.tina.mr9.data.source.DataSource
import com.tina.mr9.data.source.Repository
import com.tina.mr9.data.source.local.LocalDataSource
import com.tina.mr9.data.source.remote.RemoteDataSource

/**
 * A Service Locator for the [Repository].
 */
object ServiceLocator {

    @Volatile
    var repository: Repository? = null
        @VisibleForTesting set
//
    fun provideTasksRepository(context: Context): Repository {
        synchronized(this) {
            return repository
                ?: createStylishRepository(context)
        }
    }

    private fun createStylishRepository(context: Context): Repository {
        return DefaultRepository(
            RemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): DataSource {
        return LocalDataSource(context)
    }
}