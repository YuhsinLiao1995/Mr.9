package com.tina.mr9.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.tina.mr9.data.source.DefaultStylishRepository
import com.tina.mr9.data.source.StylishDataSource
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.data.source.local.StylishLocalDataSource
import com.tina.mr9.data.source.remote.StylishRemoteDataSource

/**
 * A Service Locator for the [StylishRepository].
 */
object ServiceLocator {

    @Volatile
    var stylishRepository: StylishRepository? = null
        @VisibleForTesting set
//
    fun provideTasksRepository(context: Context): StylishRepository {
        synchronized(this) {
            return stylishRepository
                ?: createStylishRepository(context)
        }
    }

    private fun createStylishRepository(context: Context): StylishRepository {
        return DefaultStylishRepository(
            StylishRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): StylishDataSource {
        return StylishLocalDataSource(context)
    }
}