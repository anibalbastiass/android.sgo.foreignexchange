package com.anibalbastias.android.foreignexchange.presentation.ui.currencies

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.presentation.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */

//@RunWith(AndroidJUnit4::class)
//class CurrenciesFragmentTest {
//
//    private lateinit var fragmentScenario: FragmentScenario<TestCurrenciesFragment>
//
//    @get:Rule
//    val mActivityRule: ActivityTestRule<MainActivity> =
//        ActivityTestRule(MainActivity::class.java, true, false)
//
//
//    @Before
//    fun setUp() {
//        fragmentScenario = launchFragmentInContainer(Bundle(), R.style.Theme_AppCompat)
//        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
//    }
//
//    @Test
//    fun `should display Toolbar`() {
//        onView(ViewMatchers.withId(R.id.currenciesToolbar))
//            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//    }
//
//
//    class TestCurrenciesFragment : CurrenciesFragment() {
//
//    }
//}