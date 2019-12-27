package com.anibalbastias.android.foreignexchange.di.component

import com.anibalbastias.android.foreignexchange.presentation.MainActivity
import com.anibalbastias.android.foreignexchange.base.module.component.BaseApplicationComponent
import com.anibalbastias.android.foreignexchange.di.module.*
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.CurrenciesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ForeignExchangeRepositoryModule::class,
        ViewModelModule::class,
        ForeignExchangeAPIModule::class]
)

interface ApplicationComponent : BaseApplicationComponent, FragmentInjector {
    fun inject(mainActivity: MainActivity)
}

interface FragmentInjector {
    fun inject(currenciesFragment: CurrenciesFragment)
}