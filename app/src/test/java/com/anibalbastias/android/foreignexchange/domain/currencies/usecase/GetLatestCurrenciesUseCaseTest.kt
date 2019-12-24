package com.anibalbastias.android.foreignexchange.domain.currencies.usecase

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeRemoteCurrencies
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */
class GetLatestCurrenciesUseCaseTest {

    private val postExecutionThread = mock<APIPostExecutionThread>()
    private val repository = mock<ICurrenciesRepository>()
    private val useCase = GetLatestCurrenciesUseCase(repository, postExecutionThread)

    @Test // given when then
    fun `given any latest currencies, when execute, then completes`() {
        stubRepositoryGetLatestCurrencies(Single.just(makeRemoteCurrencies()))


        val testObserver = useCase.buildUseCaseObservable(String.empty()).test()
        testObserver.assertComplete()
    }

    @Test // given when then
    fun `given any latest currencies, when execute, then returns data`() {
        val domainTermDeposit = makeRemoteCurrencies()
        stubRepositoryGetLatestCurrencies(Single.just(domainTermDeposit))

        val testObserver = useCase.buildUseCaseObservable(String.empty()).test()
        testObserver.assertValue(domainTermDeposit)
    }

    private fun stubRepositoryGetLatestCurrencies(single: Single<RemoteCurrencies>) {
        whenever(repository.getLatestCurrencies(String.empty())).thenReturn(single)
    }
}