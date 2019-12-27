package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import javax.inject.Inject


/**
 * Created by anibalbastias on 2019-11-25.
 */

open class CurrenciesUiMapper @Inject constructor() {

    fun RemoteCurrencies.fromRemoteToUi() = UiCurrencies(
        rates = rates,
        base = base,
        date = date
    )

}
