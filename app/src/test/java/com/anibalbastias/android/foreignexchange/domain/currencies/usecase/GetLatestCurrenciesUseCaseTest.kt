package com.anibalbastias.android.foreignexchange.domain.currencies.usecase

import com.anibalbastias.android.foreignexchange.base.module.coroutines.scope.ManagedCoroutineScope
import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeRemoteCurrencies
import com.anibalbastias.android.foreignexchange.presentation.util.TestScope
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper.CurrenciesUiMapper
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.presentation.util.LiveResult
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */
@ExperimentalCoroutinesApi
class GetLatestCurrenciesUseCaseTest {

    private val repository = mock<ICurrenciesRepository>()
    private val liveData = mock<LiveResult<UiCurrencies>>()
    private val mapper = mock<CurrenciesUiMapper>()
    private val useCase = GetLatestCurrenciesUseCase(repository)

    private val testDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope: ManagedCoroutineScope =
        TestScope(
            testDispatcher
        )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test // given when then
    fun `given any latest currencies, when execute, then completes`() {

        with(mapper) {
            stubRepositoryGetLatestCurrencies(makeRemoteCurrencies().fromRemoteToUi())
        }

        managedCoroutineScope.launch {
            withContext(testDispatcher) {
                whenever(useCase.execute(liveData, String.empty())).then { }
            }
        }
    }

    private fun stubRepositoryGetLatestCurrencies(currencies: UiCurrencies) {
        runBlockingTest {
            whenever(repository.getLatestCurrencies(String.empty())).thenReturn(currencies)
        }
    }
}