package com.tina.mr9

import android.app.Application
import android.content.Context
import com.tina.mr9.data.source.StylishRepository
import com.tina.mr9.util.ServiceLocator
import kotlin.properties.Delegates

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * An application that lazily provides a repository. Note that this Service Locator pattern is
 * used to simplify the sample. Consider a Dependency Injection framework.
 */
class Mr9Application : Application() {

    // Depends on the flavor,
    val stylishRepository: StylishRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: Mr9Application by Delegates.notNull()
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Mr9Application.appContext = applicationContext
        instance = this
    }

    fun isLiveDataDesign() = true
}
