package com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.repository

import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiService
import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.model.CurrenciesData
import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by anibalbastias on 21-11-19.
 */

@Singleton
class CurrenciesRepositoryImpl @Inject constructor(private val foreignExchangeApiService: ForeignExchangeApiService) :
    ICurrenciesRepository {

    override fun getLatestCurrencies(base: String): Flowable<CurrenciesData> =
        foreignExchangeApiService.getLatestCurrencies(base)

}