package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.view.BaseViewModel
import com.anibalbastias.android.foreignexchange.domain.currencies.usecase.GetLatestCurrenciesUseCase
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencyItem
import com.anibalbastias.android.foreignexchange.presentation.util.LiveResult
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import com.anibalbastias.android.foreignexchange.presentation.util.getFlagUrlByBase
import javax.inject.Inject


/**
 * Created by anibalbastias on 2019-11-25.
 */

open class CurrenciesViewModel @Inject constructor(
    private val getLatestCurrenciesUseCase: GetLatestCurrenciesUseCase
) : BaseViewModel() {

    // region Observables
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isError: ObservableBoolean = ObservableBoolean(false)

    var currencyList: ObservableField<ArrayList<UiCurrencyItem>> =
        ObservableField(arrayListOf())
    var currencySelected: ObservableField<UiCurrencyItem> =
        ObservableField(UiCurrencyItem())
    var currencyFactor: ObservableDouble = ObservableDouble(1.0)

    var itemPosition: ObservableInt = ObservableInt(0)
    // endregion

    var currencyItemLayout: Int? = R.layout.view_cell_currency_item

    //region Live Data
    private val getLatestUiCurrenciesLiveData = LiveResult<UiCurrencies>()

    fun getLatestCurrenciesLiveData() = getLatestUiCurrenciesLiveData
    //endregion

    fun getFormattedValue(value: String?) =
        "${String.format("%.2f", currencyFactor.get() * value?.toDouble()!!).toDouble()}"

    fun getLatestCurrenciesData() {
        isLoading.set(true)

        getLatestCurrenciesUseCase.execute(
            liveData = getLatestUiCurrenciesLiveData,
            params = currencySelected.get()?.title ?: String.empty()
        )
    }

    fun getCurrencyItem(it: Map.Entry<String, Double>): UiCurrencyItem =
        UiCurrencyItem(
            title = it.key,
            imageUrl = getFlagUrlByBase(it.key),
            defaultValue = ObservableField(it.value.toString()),
            value = ObservableField(
                "${String.format(
                    "%.2f",
                    currencyFactor.get() * it.value
                ).toDouble()}"
            ),
            currency = currencySelected.get()?.currency
        )

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty()) {
            if (!s.startsWith(".") && !s.endsWith("."))
                currencyFactor.set(s.toString().toDouble())
        } else {
            currencyFactor.set(1.0)
        }

        currencyList.get()?.forEach {
            it.value?.set(getFormattedValue(it.value.get()))
        }
    }

    fun onFlagCurrencyChange(newValue: Any) {
        val value = newValue as? UiCurrencyItem
        currencySelected.set(value)
        currencyList.get()?.forEach {
            it.currency?.set(currencySelected.get()?.title)
        }
    }

    fun setLatestCurrenciesUi(viewData: UiCurrencies?) {
        // Notify observers
        isLoading.set(false)
        isError.set(false)

        // Set data
        var currencies = mutableListOf<UiCurrencyItem>()

        // If haven't items, only add
        if (currencyList.get()?.isEmpty() == true) {
            viewData?.rates?.forEach {
                currencies.add(getCurrencyItem(it))
            }
        } else {
            currencies = currencyList.get()!!

            val keyList = ArrayList(viewData?.rates?.keys!!)
            val valueList = ArrayList(viewData.rates.values)

            // Match two arrays and compare
            keyList.zip(currencies).mapIndexed { index, pair ->
                if (pair.first == pair.second.title) {
                    currencies[index].value?.set(getFormattedValue(valueList[index].toString()))
                }
            }
        }
        currencyList.set(currencies as ArrayList<UiCurrencyItem>?)
    }
}