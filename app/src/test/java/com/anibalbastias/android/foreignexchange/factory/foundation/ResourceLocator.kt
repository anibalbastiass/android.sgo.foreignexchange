package com.anibalbastias.android.foreignexchange.factory.foundation

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.anibalbastias.android.foreignexchange.presentation.ForeignExchangeApplication
import org.robolectric.RuntimeEnvironment


object ResourceLocator {

    fun getString(stringId: Int): String {
        return getApplicationContext<ForeignExchangeApplication>().getString(stringId)
    }

    fun getStringWithArgument(stringId: Int, argument: String): String {
        return RuntimeEnvironment.application.getString(stringId, argument)
    }


}