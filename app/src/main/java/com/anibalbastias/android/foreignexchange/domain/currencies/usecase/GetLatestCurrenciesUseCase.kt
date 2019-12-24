package com.anibalbastias.android.foreignexchange.domain.currencies.usecase

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.domain.base.interactor.FlowableUseCase
import com.anibalbastias.android.foreignexchange.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.foreignexchange.domain.base.executor.APIThreadExecutor
import com.anibalbastias.android.foreignexchange.domain.base.interactor.SingleUseCase
import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class GetLatestCurrenciesUseCase @Inject constructor(
    private val currenciesRepository: ICurrenciesRepository,
    postExecutionThread: APIPostExecutionThread
) : SingleUseCase<RemoteCurrencies, String?>(postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?): Single<RemoteCurrencies> =
        currenciesRepository.getLatestCurrencies(params ?: String.empty())

}