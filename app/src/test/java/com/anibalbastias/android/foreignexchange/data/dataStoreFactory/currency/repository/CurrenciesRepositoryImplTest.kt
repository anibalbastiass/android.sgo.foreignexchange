package com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.repository

import com.anibalbastias.android.foreignexchange.base.module.coroutines.scope.ManagedCoroutineScope
import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiService
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeRemoteCurrencies
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper.CurrenciesUiMapper
import com.anibalbastias.android.foreignexchange.presentation.util.TestScope
import com.anibalbastias.android.foreignexchange.presentation.util.mock.CallFake
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class CurrenciesRepositoryImplTest {

    private val restApi = mock<ForeignExchangeApiService>()
    private val mapper = mock<CurrenciesUiMapper>()
    private val testDispatcher = TestCoroutineDispatcher()
    private val currenciesRemoteImpl = CurrenciesRepositoryImpl(restApi, mapper)

    private val managedCoroutineScope: ManagedCoroutineScope =
        TestScope(
            testDispatcher
        )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `given RemoteCurrency, when GetLatestCurrencies, then assert not null`() {

        stubRestApiGetLatestCurrencies(makeRemoteCurrencies())

        managedCoroutineScope.launch {
            withContext(testDispatcher) {
                val response = currenciesRemoteImpl.getLatestCurrencies("HRK")
                Assert.assertNotNull(response)
            }
        }
    }

    @Test
    fun `given RemoteCurrency, when GetLatestCurrencies, then returns data`() {
        val remoteCurrencies = makeRemoteCurrencies()
        stubRestApiGetLatestCurrencies(remoteCurrencies)

        managedCoroutineScope.launch {
            withContext(testDispatcher) {
                val response = currenciesRemoteImpl.getLatestCurrencies("HRK")
                Assert.assertEquals(response, remoteCurrencies)
            }
        }
    }

    private fun stubRestApiGetLatestCurrencies(currencies: RemoteCurrencies) {
        whenever(restApi.getLatestCurrencies("HRK")).thenReturn(CallFake.buildSuccess(currencies))
    }
}