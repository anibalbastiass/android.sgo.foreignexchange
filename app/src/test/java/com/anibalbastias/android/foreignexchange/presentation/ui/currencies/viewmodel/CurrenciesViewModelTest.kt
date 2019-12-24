package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anibalbastias.android.foreignexchange.base.view.ResourceState
import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.domain.currencies.usecase.GetLatestCurrenciesUseCase
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeMapEntryCurrency
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeRemoteCurrencies
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeUiCurrencies
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeUiCurrencyItem
import com.anibalbastias.android.foreignexchange.factory.CurrencyFactory.makeUiCurrencyListByBase
import com.anibalbastias.android.foreignexchange.factory.foundation.RandomFactory
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper.CurrenciesUiMapper
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

/**
 * Created by Anibal Bastias Soto on 2019-12-19.
 */

@RunWith(JUnit4::class)
class CurrenciesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mapper = mock<CurrenciesUiMapper>()
    private val getCurrenciesUseCase = mock<GetLatestCurrenciesUseCase>()
    private val viewModel = CurrenciesViewModel(getCurrenciesUseCase, mapper)
    @Captor
    private val captor = argumentCaptor<DisposableSingleObserver<RemoteCurrencies>>()

    @Test
    fun `when LatestCustomers Live Data, then return not null`() {
        assertNotNull(viewModel.getLatestCurrenciesLiveData())
    }

    @Test
    fun `when getLatestCurrencies, then post Loading state`() {
        viewModel.getLatestCurrenciesData()

        assert(viewModel.getLatestCurrenciesLiveData().value?.status == ResourceState.LOADING)
    }

    @Test
    fun `given LatestCurrencies, when get LatestCurrencies on success, then post success state`() {
        val currencies = makeRemoteCurrencies()

        viewModel.getLatestCurrenciesData()
        verify(getCurrenciesUseCase).execute(captor.capture(), eq("HRK"))
        captor.firstValue.onSuccess(currencies)

        assert(viewModel.getLatestCurrenciesLiveData().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun `given LatestCurrencies, when getLatestCurrencies on success, then post success state with data`() {
        val currencies = makeRemoteCurrencies()
        val currenciesUi = makeUiCurrencies()
        stubCurrenciesMapperToUi(currencies, currenciesUi)

        viewModel.getLatestCurrenciesData()
        verify(getCurrenciesUseCase).execute(captor.capture(), eq("HRK"))
        captor.firstValue.onSuccess(currencies)

        assertEquals(currenciesUi, viewModel.getLatestCurrenciesLiveData().value?.data)
    }

    private fun stubCurrenciesMapperToUi(currencies: RemoteCurrencies, currenciesUi: UiCurrencies) {
        whenever(mapper.executeMapping(currencies)).thenReturn(currenciesUi)
    }

    @Test
    fun `when GetLatestCurrencies on error, then post error state`() {

        viewModel.getLatestCurrenciesData()
        verify(getCurrenciesUseCase).execute(captor.capture(), eq("HRK"))
        captor.firstValue.onError(Exception())

        assert(viewModel.getLatestCurrenciesLiveData().value?.status == ResourceState.ERROR)
    }

    @Test
    fun `when getLatestCurrencies on error, then post error state with message`() {

        viewModel.getLatestCurrenciesData()
        verify(getCurrenciesUseCase).execute(captor.capture(), eq("HRK"))
        captor.firstValue.onError(Exception())

        assertEquals(null, viewModel.getLatestCurrenciesLiveData().value?.message)
    }

    @Test
    fun `when on cleared, then Dispose`() {
        viewModel.onCleared()
        verify(getCurrenciesUseCase).dispose()
    }

    @Test
    fun `when getFormattedValue, then returns a value`() {
        viewModel.currencyFactor.set(1.0)
        assert(viewModel.getFormattedValue("1.23456") == "1.23")
    }

    @Test
    fun `when getCurrencyItem, then returns UICurrencyItem data`() {
        val currency = RandomFactory.generateString()
        viewModel.currencyFactor.set(1.0)
        viewModel.currencySelected.set(makeUiCurrencyItem(currency))

        makeMapEntryCurrency(count = 5).map {
            assert(viewModel.getCurrencyItem(it).currency == viewModel.currencySelected.get()?.currency)
        }
    }

    @Test
    fun `give Edit Text with value, when onTextChanged for EditText, then update CurrencyFactor`() {
        val factor = RandomFactory.generateDouble()
        val baseCurrency = RandomFactory.generateString()
        val currencies = makeUiCurrencyListByBase(count = 5, base = baseCurrency)

        viewModel.apply {
            currencyList.set(currencies)
            currencyFactor.set(factor)
            onTextChanged(factor.toString(), 0, 0, 0)

            currencyList.get()?.forEach {
                it.value?.set(getFormattedValue(it.value?.get()))
                assert(
                    getFormattedValue(it.value?.get()) == "${String.format(
                        "%.2f",
                        currencyFactor.get() * it.value?.get()?.toDouble()!!
                    ).toDouble()}"
                )
            }
        }
    }

    @Test
    fun `give Edit Text without value, when onTextChanged for EditText, then update CurrencyFactor`() {
        val baseCurrency = RandomFactory.generateString()
        val currencies = makeUiCurrencyListByBase(count = 5, base = baseCurrency)

        viewModel.apply {
            currencyList.set(currencies)
            currencyFactor.set(1.0)
            onTextChanged(String.empty(), 0, 0, 0)

            currencyList.get()?.forEach {
                it.value?.set(getFormattedValue(it.value?.get()))
                assert(
                    getFormattedValue(it.value?.get()) == "${String.format(
                        "%.2f",
                        currencyFactor.get() * it.value?.get()?.toDouble()!!
                    ).toDouble()}"
                )
            }
        }
    }

    @Test
    fun `when onFlagCurrency changes, then currency selected updates`() {
        val currency = RandomFactory.generateString()
        val currencyUiSelected = makeUiCurrencyItem(currency)
        val currencies = makeUiCurrencyListByBase(count = 5, base = currency)

        viewModel.apply {
            currencyList.set(currencies)
            onFlagCurrencyChange(currencyUiSelected)

            assert(viewModel.currencySelected.get() == currencyUiSelected)

            currencyList.get()?.forEach {
                it.currency?.set(currencySelected.get()?.title)
                assert(it.currency?.get() == currencySelected.get()?.title)
            }
        }
    }

    @Test
    fun `give new Latest currencies for first time, when setLatestCurrenciesUi, then creates currencies list`() {
        val currencies = makeUiCurrencies()

        viewModel.apply {
            isLoading.set(false)
            isError.set(false)
            currencyList.set(arrayListOf())

            assert(viewModel.currencyList.get()?.isEmpty() == true)

            setLatestCurrenciesUi(currencies)

            assert(viewModel.currencyList.get()?.isEmpty() == false)
        }
    }

    @Test
    fun `give new Latest currencies, when setLatestCurrenciesUi, then updates currencies list`() {
        val currencies = makeUiCurrencies()
        val baseCurrency = RandomFactory.generateString()

        val oldCurrencyList = makeUiCurrencyListByBase(count = 5, base = baseCurrency)
        val newCurrencyList = makeUiCurrencyListByBase(count = 5, base = baseCurrency)

        viewModel.apply {
            isLoading.set(false)
            isError.set(false)
            currencyList.set(oldCurrencyList)

            setLatestCurrenciesUi(currencies)

            currencyList.get()?.zip(newCurrencyList)?.mapIndexed { index, pair ->
                if (pair.first.currency == pair.second.currency) {
                    currencyList.get()?.get(index)
                        ?.value?.set(getFormattedValue(oldCurrencyList[index].value?.get()))

                    assert(
                        currencyList.get()?.get(index)?.value?.get() == getFormattedValue(
                            oldCurrencyList[index].value?.get()
                        )
                    )
                }
            }
        }

        assert(viewModel.currencyList.get()?.isNotEmpty() == true)
    }
}