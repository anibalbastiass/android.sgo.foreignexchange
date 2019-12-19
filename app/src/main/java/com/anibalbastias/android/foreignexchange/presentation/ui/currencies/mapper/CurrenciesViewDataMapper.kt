package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.model.CurrenciesData
import com.anibalbastias.android.foreignexchange.domain.base.Mapper
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.CurrenciesViewData
import javax.inject.Inject


/**
 * Created by anibalbastias on 2019-11-25.
 */

open class CurrenciesViewDataMapper @Inject constructor() : Mapper<CurrenciesViewData?, CurrenciesData?> {

    override fun executeMapping(type: CurrenciesData?): CurrenciesViewData? {
        return type?.let {
            CurrenciesViewData(
                rates = it.rates,
                base = it.base,
                date = it.date
            )
        }
    }
}