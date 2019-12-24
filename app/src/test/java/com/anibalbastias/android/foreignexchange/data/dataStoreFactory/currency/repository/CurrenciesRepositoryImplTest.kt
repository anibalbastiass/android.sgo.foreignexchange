package com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.repository

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiService
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeRemoteCurrencies
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */

@RunWith(JUnit4::class)
class CurrenciesRepositoryImplTest {

    private val restApi = mock<ForeignExchangeApiService>()
    private val currenciesRemoteImpl = CurrenciesRepositoryImpl(restApi)

    @Test
    fun `given RemoteCurrency, when GetLatestCurrencies, then completes`() {
        stubRestApiGetLatestCurrencies(Single.just(makeRemoteCurrencies()))

        val testObserver =
            currenciesRemoteImpl.getLatestCurrencies(String.empty()).test()

        testObserver.assertComplete()
    }

    @Test
    fun `given RemoteCurrency, when GetLatestCurrencies, then returns data`() {
        val remoteCurrencies = makeRemoteCurrencies()
        stubRestApiGetLatestCurrencies(Single.just(remoteCurrencies))

        val testObserver = currenciesRemoteImpl.getLatestCurrencies(String.empty()).test()

        testObserver.assertValue(remoteCurrencies)
    }

    private fun stubRestApiGetLatestCurrencies(single: Single<RemoteCurrencies>) {
        whenever(restApi.getLatestCurrencies(String.empty())).thenReturn(single)
    }
}