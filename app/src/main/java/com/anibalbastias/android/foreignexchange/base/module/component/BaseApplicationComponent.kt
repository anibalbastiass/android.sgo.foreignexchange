package com.anibalbastias.android.foreignexchange.base.module.component

import com.anibalbastias.android.foreignexchange.presentation.ForeignExchangeApplication
import com.anibalbastias.android.foreignexchange.base.module.executor.JobExecutor
import com.anibalbastias.android.foreignexchange.base.module.executor.UIThread


interface BaseApplicationComponent {

    fun inject(application: ForeignExchangeApplication)
    fun threadExecutor(): JobExecutor
    fun postExecutionThread(): UIThread
}