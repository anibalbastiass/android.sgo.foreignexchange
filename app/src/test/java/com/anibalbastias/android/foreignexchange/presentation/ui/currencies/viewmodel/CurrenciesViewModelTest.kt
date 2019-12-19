package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anibalbastias.android.foreignexchange.domain.currencies.usecase.GetLatestCurrenciesUseCase
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper.CurrenciesUiMapper
import com.nhaarman.mockitokotlin2.*
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert.*
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
//    @Captor
//    private val captor = argumentCaptor<DisposableSingleObserver<List<Deposit>>>()
//
//    @Test
//    fun whenLiveData_thenReturnsNotNull() {
//        assertNotNull(viewModel.liveData())
//    }
//
//    @Test
//    fun whenGetDeposits_thenPostLoadingState() {
//        viewModel.getDeposits()
//
//        assert(viewModel.liveData().value is ListState.Loading)
//    }
//
//    @Test
//    fun givenDeposits_whenGetDepositsAndOnSuccess_thenPostSuccessState() {
//        val deposit = makeDeposit()
//
//        viewModel.getDeposits()
//        verify(getCurrenciesUseCase).execute(captor.capture(), eq(null))
//        captor.firstValue.onSuccess(listOf(deposit))
//
//        assert(viewModel.liveData().value is ListState.Success)
//    }
//
//    @Test
//    fun givenDeposits_whenGetDepositsAndOnSuccess_thenPostSuccessStateWithData() {
//        val deposit = makeDeposit()
//        val depositUi = makeDepositUi()
//        stubDetailMapperToUi(deposit, depositUi)
//
//        viewModel.getDeposits()
//        verify(getCurrenciesUseCase).execute(captor.capture(), eq(null))
//        captor.firstValue.onSuccess(listOf(deposit))
//
//        assertEquals(listOf(depositUi), viewModel.liveData().value?.data)
//    }
//
//    private fun stubDetailMapperToUi(deposit: Deposit, depositUi: DepositUi) {
//        whenever(mapper.mapToUi(deposit)).thenReturn(depositUi)
//    }
//
//    @Test
//    fun whenGetDepositsAndOnError_thenPostErrorState() {
//
//        viewModel.getDeposits()
//        verify(getCurrenciesUseCase).execute(captor.capture(), eq(null))
//        captor.firstValue.onError(Exception())
//
//        assert(viewModel.liveData().value is ListState.Error)
//    }
//
//    @Test
//    fun whenGetDepositsAndOnError_thenPostErrorStateWithMessage() {
//
//        viewModel.getDeposits()
//        verify(getCurrenciesUseCase).execute(captor.capture(), eq(null))
//        captor.firstValue.onError(Exception())
//
//        assertEquals(R.string.error_network_default, viewModel.liveData().value?.errorId)
//    }
//
//    @Test
//    fun givenEmptyDeposits_whenGetDepositsAndOnSuccess_thenPostEmptyState() {
//        val emptyDeposits = makeDepositList(0)
//
//        viewModel.getDeposits()
//        verify(getCurrenciesUseCase).execute(captor.capture(), eq(null))
//        captor.firstValue.onSuccess(emptyDeposits)
//
//        assert(viewModel.liveData().value is ListState.Empty)
//    }
}