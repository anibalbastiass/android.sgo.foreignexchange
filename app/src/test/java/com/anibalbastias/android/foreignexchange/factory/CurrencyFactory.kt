package com.anibalbastias.android.foreignexchange.factory

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.factory.foundation.RandomFactory

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */
object CurrencyFactory {

    fun makeRemoteCurrencies() = RemoteCurrencies(
        rates = makeRemoteCurrenciesRates(),
        base = RandomFactory.generateString(),
        date = RandomFactory.generateString()
    )

    private fun makeRemoteCurrenciesRates(): HashMap<String, Double> {
        val hashMap = hashMapOf<String, Double>()
        (0..5).map { hashMap[RandomFactory.generateString()] = RandomFactory.generateDouble() }
        return hashMap
    }

}