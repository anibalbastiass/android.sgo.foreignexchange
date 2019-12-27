package com.anibalbastias.android.foreignexchange.domain.currencies.usecase

import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.base.module.coroutines.ResultUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

open class GetLatestCurrenciesUseCase @Inject constructor(private val currenciesRepository: ICurrenciesRepository) :
    ResultUseCase<String, UiCurrencies>(
        backgroundContext = Dispatchers.IO,
        foregroundContext = Dispatchers.Main
    ) {

    override suspend fun executeOnBackground(params: String): UiCurrencies? =
        currenciesRepository.getLatestCurrencies(params)

}