package com.anibalbastias.android.foreignexchange.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.view.BaseModuleFragment
import com.anibalbastias.android.foreignexchange.di.component.ApplicationComponent
import com.anibalbastias.android.foreignexchange.di.component.DaggerApplicationComponent
import com.anibalbastias.android.foreignexchange.di.module.ApplicationModule
import com.bumptech.glide.request.target.ViewTarget.*

var context: ForeignExchangeApplication? = null
fun getAppContext(): ForeignExchangeApplication {
    return context!!
}

/**
 * Created by anibalbastias on 2019-11-25.
 */

class ForeignExchangeApplication : MultiDexApplication() {

    companion object {
        lateinit var appContext: Context
        var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
        context = this
        appContext = this
    }
}

private fun buildDagger(context: Context): ApplicationComponent {
    if (ForeignExchangeApplication.applicationComponent == null) {
        ForeignExchangeApplication.applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(context as ForeignExchangeApplication))
            .build()
    }
    return ForeignExchangeApplication.applicationComponent!!
}

fun Context.appComponent(): ApplicationComponent {
    return buildDagger(this.applicationContext)
}

fun Fragment.appComponent(): ApplicationComponent {
    return buildDagger(this.context!!.applicationContext)
}

fun BaseModuleFragment.appComponent(): ApplicationComponent {
    return buildDagger(this.context!!.applicationContext)
}