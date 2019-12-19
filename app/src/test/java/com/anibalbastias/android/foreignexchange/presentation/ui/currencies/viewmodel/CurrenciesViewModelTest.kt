package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel

import com.anibalbastias.android.foreignexchange.base.subscriber.BaseSubscriber
import com.anibalbastias.android.foreignexchange.base.view.ResourceState
import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.model.CurrenciesData
import com.anibalbastias.android.foreignexchange.domain.currencies.usecase.GetLatestCurrenciesUseCase
import com.anibalbastias.android.foreignexchange.presentation.BaseDataManager.Companion.getErrorMessage
import com.anibalbastias.android.foreignexchange.presentation.BaseUnitTest
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.mapper.breeds.BreedsViewDataMapper
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.breeds.ForeignExchangeViewData
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by anibalbastias on 2019-12-01.
 */

class CurrenciesViewModelTest : BaseUnitTest() {

    override fun showErrorMessage(): Boolean = false

    override fun initCaptors() {
        captorBreeds = argumentCaptor()
    }

    override fun initDataMocks() {
        getLatestCurrenciesUseCase = mock()
        getRandomImageBreedUseCase = mock()
        getDogImagesByBreedUseCase = mock()
        breedsViewDataMapper = mock()
    }

    override fun initViewModel() {
        breedsViewModel = BreedsViewModel(
            getLatestCurrenciesUseCase,
            getRandomImageBreedUseCase,
            getDogImagesByBreedUseCase,
            breedsViewDataMapper
        )
    }

    // region Mocked vars
    @Mock
    lateinit var getLatestCurrenciesUseCase: GetLatestCurrenciesUseCase
    @Mock
    lateinit var getRandomImageBreedUseCase: GetRandomImageBreedUseCase
    @Mock
    lateinit var getDogImagesByBreedUseCase: GetDogImagesByBreedUseCase
    @Mock
    lateinit var breedsViewDataMapper: BreedsViewDataMapper
    // endregion

    // region Captors vars
    @Captor
    private lateinit var captorBreeds: KArgumentCaptor<BaseSubscriber<ForeignExchangeViewData, CurrenciesData>>
    // endregion

    private lateinit var breedsViewModel: BreedsViewModel

    // region Setup Data

    @Before
    fun setUp() {
        initCaptors()
        initDataMocks()
        initViewModel()
    }
    // endregion

    // region Use Case Execute Testing

    @Test
    fun testGetBreedsUseCase() {
        breedsViewModel.getBreedListAllData()
        Mockito.verify(getLatestCurrenciesUseCase, times(1)).execute(any(), anyOrNull())
    }

    @Test
    fun testGetRandomImageBreedUseCase() {
        breedsViewModel.getRandomImageBreed("germanshepherd")
        Mockito.verify(getRandomImageBreedUseCase, times(1)).execute(any(), anyOrNull())
    }

    @Test
    fun testGetDogImagesByBreedUseCase() {
        breedsViewModel.getDogImagesByBreed("akita")
        Mockito.verify(getDogImagesByBreedUseCase, times(1)).execute(any(), anyOrNull())
    }
    // endregion

    // region LiveData Status GetBreeds

    @Test
    fun testStatusLiveDataGetBreedsCompleteItem_isLoading() {
        breedsViewModel.let { viewModel ->
            breedsViewModel.getBreedListAllData()

            Mockito.verify(getLatestCurrenciesUseCase, times(1))
                .execute(captorBreeds.capture(), anyOrNull())

            assertEquals(true, viewModel.isLoading.get())
            assertEquals(false, viewModel.isError.get())
            assertNotNull(viewModel.getBreedsLiveData().value)
            assertEquals(ResourceState.LOADING, viewModel.getBreedsLiveData().value?.status)
        }
    }

    @Test
    fun testStatusLiveDataGetBreedsCompleteItem_isError() {
        val methodName = "${object : Any() {}.javaClass.enclosingMethod?.name}()"

        breedsViewModel.let { viewModel ->
            breedsViewModel.getBreedListAllData()

            captorBreeds.let {
                Mockito.verify(getLatestCurrenciesUseCase, times(1))
                    .execute(it.capture(), anyOrNull())
                it.firstValue.onComplete()
                it.firstValue.onError(getErrorMessage(showErrorMessage(), methodName))
            }

            assertEquals(false, viewModel.isLoading.get())
            assertEquals(true, viewModel.isError.get())
            assertNotNull(viewModel.getBreedsLiveData().value)
            assertEquals(ResourceState.ERROR, viewModel.getBreedsLiveData().value?.status)
        }
    }

    @Test
    fun testStatusLiveDataGetRandomImageBreedCompleteItem_isLoading() {
        breedsViewModel.let { viewModel ->
            breedsViewModel.getRandomImageBreed("collie")

            Mockito.verify(getRandomImageBreedUseCase, times(1))
                .execute(captorBreeds.capture(), anyOrNull())

            assertEquals(false, viewModel.isError.get())
            assertNotNull(viewModel.getBreedsImageLiveData().value)
            assertEquals(ResourceState.LOADING, viewModel.getBreedsImageLiveData().value?.status)
        }
    }

    @Test
    fun testStatusLiveDataGetRandomImageBreedCompleteItem_isError() {
        val methodName = "${object : Any() {}.javaClass.enclosingMethod?.name}()"

        breedsViewModel.let { viewModel ->
            breedsViewModel.getRandomImageBreed("wolfhound")

            captorBreeds.let {
                Mockito.verify(getRandomImageBreedUseCase, times(1))
                    .execute(it.capture(), anyOrNull())
                it.firstValue.onComplete()
                it.firstValue.onError(getErrorMessage(showErrorMessage(), methodName))
            }

            assertEquals(false, viewModel.isLoading.get())
            assertEquals(true, viewModel.isError.get())
            assertNotNull(viewModel.getBreedsImageLiveData().value)
            assertEquals(ResourceState.ERROR, viewModel.getBreedsImageLiveData().value?.status)
        }
    }

    @Test
    fun testStatusLiveDataGetDogImagesByBreedCompleteItem_isLoading() {
        breedsViewModel.let { viewModel ->
            breedsViewModel.getDogImagesByBreed("terrier")

            Mockito.verify(getDogImagesByBreedUseCase, times(1))
                .execute(captorBreeds.capture(), anyOrNull())

            assertEquals(false, viewModel.isError.get())
            assertNotNull(viewModel.getDogImagesByBreedLiveData().value)
            assertEquals(ResourceState.LOADING, viewModel.getDogImagesByBreedLiveData().value?.status)
        }
    }

    @Test
    fun testStatusLiveDataGetDogImagesByBreedCompleteItem_isError() {
        val methodName = "${object : Any() {}.javaClass.enclosingMethod?.name}()"

        breedsViewModel.let { viewModel ->
            breedsViewModel.getDogImagesByBreed("redbone")

            captorBreeds.let {
                Mockito.verify(getDogImagesByBreedUseCase, times(1))
                    .execute(it.capture(), anyOrNull())
                it.firstValue.onComplete()
                it.firstValue.onError(getErrorMessage(showErrorMessage(), methodName))
            }

            assertEquals(false, viewModel.isLoading.get())
            assertEquals(true, viewModel.isError.get())
            assertNotNull(viewModel.getDogImagesByBreedLiveData().value)
            assertEquals(ResourceState.ERROR, viewModel.getDogImagesByBreedLiveData().value?.status)
        }
    }
    // endregion
}