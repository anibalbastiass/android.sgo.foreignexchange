package com.anibalbastias.android.foreignexchange.presentation.ui.currencies

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.module.getViewModel
import com.anibalbastias.android.foreignexchange.base.view.Resource
import com.anibalbastias.android.foreignexchange.helper.ViewModelUtil
import com.anibalbastias.android.foreignexchange.presentation.MainActivity
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel.CurrenciesViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */

@RunWith(AndroidJUnit4::class)
class CurrenciesFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<TestCurrenciesFragment>

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer()
    }

    @Test
    fun `should display Toolbar`() {
        onView(ViewMatchers.withId(R.id.currenciesToolbar))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    class TestCurrenciesFragment : CurrenciesFragment() {
        val viewModelMock = mock<CurrenciesViewModel>()
        val liveDataMock: MutableLiveData<Resource<UiCurrencies?>> = MutableLiveData()

        override fun setupInjection() {
            super.setupInjection()
            stubViewModel()
        }

        private fun stubViewModel() {
            whenever(viewModelMock.getLatestCurrenciesLiveData()).thenReturn(liveDataMock)
        }
    }
}