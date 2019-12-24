package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.domain.base.Mapper
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import javax.inject.Inject


/**
 * Created by anibalbastias on 2019-11-25.
 */

open class CurrenciesUiMapper @Inject constructor() : Mapper<UiCurrencies?, RemoteCurrencies?> {

    override fun executeMapping(type: RemoteCurrencies?): UiCurrencies? {
        return type?.let {
            UiCurrencies(
                rates = it.rates,
                base = it.base,
                date = it.date
            )
        }
    }
}