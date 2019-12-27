package com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.repository

import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiService
import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper.CurrenciesUiMapper
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.presentation.util.await
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by anibalbastias on 21-11-19.
 */

@Singleton
open class CurrenciesRepositoryImpl @Inject constructor(
    private val foreignExchangeApiService: ForeignExchangeApiService,
    private val currenciesUiMapper: CurrenciesUiMapper
) : ICurrenciesRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getLatestCurrencies(base: String): UiCurrencies? {
        return withContext(ioDispatcher) {
            with(currenciesUiMapper) {
                val response = foreignExchangeApiService.getLatestCurrencies(base).await()
                response?.fromRemoteToUi()
            }
        }
    }
}