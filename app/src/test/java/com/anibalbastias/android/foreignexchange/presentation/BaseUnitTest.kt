package com.anibalbastias.android.foreignexchange.presentation

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.robolectric.annotation.Config

/**
 * Created by anibalbastias on 02-09-2019.
 * */

@RunWith(JUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = ForeignExchangeApplication::class)
abstract class BaseUnitTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * This value indicate if it must be shown or hidden the dummy RuntimeException message
     * when the ERROR status of the live data is being verified in the unit test.
     **/
    abstract fun showErrorMessage(): Boolean

    /**
     * Initializes the argument captors for the use cases that will be used to testing.
     **/
    abstract fun initCaptors()

    /**
     * Mock the necessary variables that will be used to testing.
     **/
    abstract fun initDataMocks()

    /**
     * Initializes the view model that will be tested.
     **/
    abstract fun initViewModel()
}