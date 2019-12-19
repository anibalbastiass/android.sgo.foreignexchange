package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.databinding.ObservableLong
import androidx.lifecycle.MutableLiveData
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.subscriber.BaseSubscriber
import com.anibalbastias.android.foreignexchange.base.view.BaseViewModel
import com.anibalbastias.android.foreignexchange.base.view.Resource
import com.anibalbastias.android.foreignexchange.base.view.ResourceState
import com.anibalbastias.android.foreignexchange.domain.currencies.usecase.GetLatestCurrenciesUseCase
import com.anibalbastias.android.foreignexchange.presentation.context
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper.CurrenciesViewDataMapper
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.CurrenciesViewData
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.CurrencyItemViewData
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import com.anibalbastias.android.foreignexchange.presentation.util.getFlagUrlByBase
import javax.inject.Inject


/**
 * Created by anibalbastias on 2019-11-25.
 */

class CurrenciesViewModel @Inject constructor(
    private val getLatestCurrenciesUseCase: GetLatestCurrenciesUseCase,
    private val currenciesViewDataMapper: CurrenciesViewDataMapper
) : BaseViewModel() {

    // region Observables
    var isLoading: ObservableBoolean = ObservableBoolean(false)
    var isError: ObservableBoolean = ObservableBoolean(false)

    var currencyList: ObservableField<ArrayList<CurrencyItemViewData>> =
        ObservableField(arrayListOf())
    var currencySelected: ObservableField<CurrencyItemViewData> =
        ObservableField(CurrencyItemViewData())
    var currencyFactor: ObservableLong = ObservableLong(1)

    var itemPosition: ObservableInt = ObservableInt(0)
    // endregion

    var currencyItemLayout: Int? = R.layout.view_cell_currency_item

    override fun onCleared() {
        getLatestCurrenciesUseCase.dispose()
        super.onCleared()
    }

    //region Live Data
    private val getLatestCurrenciesLiveData: MutableLiveData<Resource<CurrenciesViewData?>> =
        MutableLiveData()

    fun getLatestCurrenciesLiveData() = getLatestCurrenciesLiveData
    //endregion

    fun getFormattedValue(value: String?) =
        "${String.format("%.2f", currencyFactor.get() * value?.toDouble()!!).toDouble()}"

    fun getLatestCurrenciesData(base: String? = "HRK") {
        isLoading.set(true)
        getLatestCurrenciesLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        return getLatestCurrenciesUseCase.execute(
            BaseSubscriber(
                context?.applicationContext, this, currenciesViewDataMapper,
                getLatestCurrenciesLiveData, isLoading, isError
            ), base
        )
    }

    fun getCurrencyItem(it: Map.Entry<String, Double>): CurrencyItemViewData =
        CurrencyItemViewData(
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
            currencyFactor.set(s.toString().toLong())
        } else {
            currencyFactor.set(1)
        }

        currencyList.get()?.forEach {
            it.value?.set(getFormattedValue(it.value.get()))
        }
    }

    fun onFlagCurrencyChange(newValue: Any) {
        val value = newValue as? CurrencyItemViewData
        currencySelected.set(value)
        currencyList.get()?.forEach {
            it.currency?.set(currencySelected.get()?.title)
        }
    }
}