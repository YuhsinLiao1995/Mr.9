package com.tina.mr9

import android.os.Bundle
import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.tina.mr9.rate.RateFragment
import com.tina.mr9.search.SearchFragment

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tina.mr9", appContext.packageName)
    }

    @RunWith(AndroidJUnit4::class)
    @LargeTest
    class HelloWorldEspressoTest {

        @get:Rule
        val activityRule = ActivityTestRule(MainActivity::class.java)

        @Test fun listGoesOverTheFold() {
            onView(withText("Hello world!")).check(matches(isDisplayed()))
        }

        private val fragmentArgs = Bundle().apply {
            null
        }

        val rateFragment = launchFragment<RateFragment>(fragmentArgs)





    }

    @RunWith(AndroidJUnit4::class)
    @LargeTest
    class SearchFragmentTest{
        @get:Rule
        val fragmentRule = launchFragment<SearchFragment>()



        @Test fun title() {

            onView(withText("Search")).perform(click())
        }
    }




}
