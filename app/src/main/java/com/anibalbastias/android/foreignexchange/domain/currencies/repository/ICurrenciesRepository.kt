package com.anibalbastias.android.foreignexchange.domain.currencies.repository

import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies

/**
 * Created by anibalbastias on 3/19/18.
 */

interface ICurrenciesRepository {
    suspend fun getLatestCurrencies(base: String): UiCurrencies?
}