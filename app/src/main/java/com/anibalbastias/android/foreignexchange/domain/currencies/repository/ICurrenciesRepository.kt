package com.anibalbastias.android.foreignexchange.domain.currencies.repository

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.model.CurrenciesData
import io.reactivex.Flowable

/**
 * Created by anibalbastias on 3/19/18.
 */
interface ICurrenciesRepository {
    fun getLatestCurrencies(base: String): Flowable<CurrenciesData>
}