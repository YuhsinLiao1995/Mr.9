package com.tina.mr9


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class FriendsTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_friends),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavView),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )

        Thread.sleep(3000)

        bottomNavigationItemView.perform(click())

        Thread.sleep(3000)

        val appCompatEditText = onView(
            allOf(
                withId(R.id.searchText),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    1
                )
            )
        )
        Thread.sleep(3000)

        appCompatEditText.perform(scrollTo(), replaceText("m"), closeSoftKeyboard())
        Thread.sleep(3000)

        val recyclerView = onView(
            allOf(
                withId(R.id.list_view),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    6
                )
            )
        )
        Thread.sleep(3000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        Thread.sleep(3000)

        val tabView = onView(
            allOf(
                withContentDescription("My Drink"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs_catalog),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        tabView.perform(click())
        Thread.sleep(3000)

        val tabView2 = onView(
            allOf(
                withContentDescription("My Bar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tabs_catalog),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        tabView2.perform(click())
        Thread.sleep(3000)
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
