package com.anibalbastias.android.foreignexchange.presentation.module

import androidx.lifecycle.ViewModel
import com.anibalbastias.android.foreignexchange.base.module.ViewModelKey
import com.anibalbastias.android.foreignexchange.base.module.module.BaseViewModelModule
import com.anibalbastias.android.foreignexchange.base.view.NavBaseViewModel
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel.CurrenciesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule : BaseViewModelModule() {

    @Binds
    @IntoMap
    @ViewModelKey(NavBaseViewModel::class)
    internal abstract fun navBaseViewModel(viewModel: NavBaseViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CurrenciesViewModel::class)
    internal abstract fun curenciesViewModel(viewModel: CurrenciesViewModel): ViewModel

}