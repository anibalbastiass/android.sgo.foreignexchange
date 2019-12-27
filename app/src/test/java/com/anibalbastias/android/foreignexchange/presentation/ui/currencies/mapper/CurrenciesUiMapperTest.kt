package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeRemoteCurrencies
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */

@RunWith(AndroidJUnit4::class)
class CurrenciesUiMapperTest {

    private val mapper = CurrenciesUiMapper()

    @Test
    fun `given Currencies Remote, when map to Ui, then returns Currencies Ui`() {
        val remoteCurrencies = makeRemoteCurrencies()

        val uiCurrencies = with(mapper) {
            remoteCurrencies.fromRemoteToUi()
        }

        assertEquals("rates", remoteCurrencies.rates, uiCurrencies.rates)
        assertEquals("base", remoteCurrencies.base, uiCurrencies.base)
        assertEquals("date", remoteCurrencies.date, uiCurrencies.date)
    }
}