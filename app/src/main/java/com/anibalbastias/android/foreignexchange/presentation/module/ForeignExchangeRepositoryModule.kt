package com.anibalbastias.android.foreignexchange.presentation.module

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.repository.CurrenciesRepositoryImpl
import com.anibalbastias.android.foreignexchange.domain.currencies.repository.ICurrenciesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ForeignExchangeRepositoryModule {

    @Binds
    abstract fun bindCurrenciesDataRepository(repository: CurrenciesRepositoryImpl): ICurrenciesRepository

}