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
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RateTest3 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.CAMERA",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    @Test
    fun rateTest3() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_rate),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        bottomNavigationItemView.perform(click())
        Thread.sleep(3000)

        val textInputEditText = onView(
            allOf(
                withId(R.id.bar_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bar_input_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        textInputEditText.perform(replaceText("d"), closeSoftKeyboard())
        Thread.sleep(3000)
        val recyclerView = onView(
            allOf(
                withId(R.id.list_view_bar),
                childAtPosition(
                    withId(R.id.basic_info),
                    14
                )
            )
        )
        Thread.sleep(3000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        Thread.sleep(3000)
        val textInputEditText2 = onView(
            allOf(
                withId(R.id.name_input),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.name_input_layout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        textInputEditText2.perform(replaceText("j"), closeSoftKeyboard())
        Thread.sleep(3000)
        val recyclerView2 = onView(
            allOf(
                withId(R.id.list_view_drink),
                childAtPosition(
                    withId(R.id.basic_info),
                    15
                )
            )
        )
        Thread.sleep(3000)
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
        Thread.sleep(3000)
        val appCompatButton = onView(
            allOf(
                withId(R.id.btn_newDrink),
                childAtPosition(
                    allOf(
                        withId(R.id.basic_info),
                        childAtPosition(
                            withId(R.id.layoutRatingBackground),
                            0
                        )
                    ),
                    4
                )
            )
        )
        Thread.sleep(3000)

        appCompatButton.perform(scrollTo(), click())

        Thread.sleep(3000)

        onView(withId(R.id.scrollView)).perform(swipeUp())

        Thread.sleep(3000)
        val textInputEditText3 = onView(
            allOf(
                withId(R.id.conetent_input),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.conetent_input_layout),
//                        0
//                    ),
//                    0
//                ),
                isDisplayed()
            )
        )
        Thread.sleep(3000)
        textInputEditText3.perform(replaceText("vodka"), closeSoftKeyboard())
        Thread.sleep(3000)
        val appCompatImageView = onView(
            allOf(
                withId(R.id.btn_add_chip),
                childAtPosition(
                    allOf(
                        withId(R.id.content),
                        childAtPosition(
                            withId(R.id.basic_info),
                            13
                        )
                    ),
                    0
                )
            )
        )
        Thread.sleep(3000)
        appCompatImageView.perform(scrollTo(), click())
        Thread.sleep(3000)
        val appCompatTextView = onView(
            allOf(
                withId(R.id.btn_next), withText("Next"),
                childAtPosition(
                    allOf(
                        withId(R.id.basic_info),
                        childAtPosition(
                            withId(R.id.layoutRatingBackground),
                            0
                        )
                    ),
                    12
                )
            )
        )
        Thread.sleep(3000)
        appCompatTextView.perform(scrollTo(), click())
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
