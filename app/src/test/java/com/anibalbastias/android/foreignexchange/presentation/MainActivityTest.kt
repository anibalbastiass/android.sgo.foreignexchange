package com.anibalbastias.android.foreignexchange.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.presentation.util.RobolectricBaseTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */

@RunWith(AndroidJUnit4::class)
class MainActivityTest : RobolectricBaseTest() {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun `should show Container Fragment`() {
        Espresso.onView(ViewMatchers.withId(R.id.container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}