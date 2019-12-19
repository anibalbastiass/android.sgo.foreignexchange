package com.anibalbastias.android.foreignexchange.domain.currencies.usecase

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.model.CurrenciesData
import com.anibalbastias.android.foreignexchange.domain.base.interactor.FlowableUseCase
import com.anibalbastias.android.foreignexchange.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.foreignexchange.domain.base.executor.APIThreadExecutor
import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import io.reactivex.Flowable
import javax.inject.Inject

open class GetLatestCurrenciesUseCase @Inject constructor(
    private val currenciesRepository: ICurrenciesRepository,
    threadExecutor: APIThreadExecutor,
    postExecutionThread: APIPostExecutionThread
) : FlowableUseCase<CurrenciesData, String?>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: String?): Flowable<CurrenciesData> =
        currenciesRepository.getLatestCurrencies(params ?: String.empty())

}