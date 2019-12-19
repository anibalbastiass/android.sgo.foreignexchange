package com.anibalbastias.android.foreignexchange.base.module.module

import com.anibalbastias.android.foreignexchange.presentation.ForeignExchangeApplication
import com.anibalbastias.android.foreignexchange.domain.base.executor.APIPostExecutionThread
import com.anibalbastias.android.foreignexchange.domain.base.executor.APIThreadExecutor
import com.anibalbastias.android.foreignexchange.base.module.executor.JobExecutor
import com.anibalbastias.android.foreignexchange.base.module.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class BaseApplicationModule(private val application: ForeignExchangeApplication) {

    @Provides
    @Singleton
    fun provideApp(): ForeignExchangeApplication = application

    @Provides
    @Singleton
    fun provideAPIThreadExecutor(jobExecutor: JobExecutor): APIThreadExecutor = jobExecutor

    @Provides
    @Singleton
    fun provideAPIPostExecutionThread(uiThread: UIThread): APIPostExecutionThread = uiThread
}