package com.anibalbastias.android.foreignexchange.domain.currencies.repository

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import io.reactivex.Single

/**
 * Created by anibalbastias on 3/19/18.
 */
interface ICurrenciesRepository {
    fun getLatestCurrencies(base: String): Single<RemoteCurrencies>
}