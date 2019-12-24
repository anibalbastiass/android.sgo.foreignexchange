package com.anibalbastias.android.foreignexchange.factory

import androidx.databinding.ObservableField
import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.factory.foundation.RandomFactory
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencyItem

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */
object CurrencyFactory {

    fun makeUiCurrencies() = UiCurrencies(
        rates = makeHashMapStringDouble(count = 5),
        base = RandomFactory.generateString(),
        date = RandomFactory.generateString()
    )

    fun makeUiCurrencyListByBase(count: Int, base: String): ArrayList<UiCurrencyItem> {
        val currencyList = arrayListOf<UiCurrencyItem>()
        (0..count).map { currencyList.add(makeUiCurrencyItem(base)) }
        return currencyList
    }

    fun makeUiCurrencyItem(currency: String) = UiCurrencyItem(
        title = RandomFactory.generateString(),
        imageUrl = RandomFactory.generateString(),
        defaultValue = ObservableField(RandomFactory.generateDouble().toString()),
        value = ObservableField(RandomFactory.generateDouble().toString()),
        currency = ObservableField(currency)
    )

    fun makeRemoteCurrencies() = RemoteCurrencies(
        rates = makeHashMapStringDouble(count = 5),
        base = RandomFactory.generateString(),
        date = RandomFactory.generateString()
    )

    private fun makeHashMapStringDouble(count: Int): HashMap<String, Double> {
        val hashMap = hashMapOf<String, Double>()
        (0..count).map { hashMap[RandomFactory.generateString()] = RandomFactory.generateDouble() }
        return hashMap
    }

    fun makeMapEntryCurrency(count: Int): HashMap<String, Double> {
        val hashMap = hashMapOf<String, Double>()
        (0..count).map { hashMap[RandomFactory.generateString()] = RandomFactory.generateDouble() }
        return hashMap
    }
}