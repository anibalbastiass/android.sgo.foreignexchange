package com.anibalbastias.android.foreignexchange.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.view.BaseModuleFragment
import com.anibalbastias.android.foreignexchange.presentation.component.ApplicationComponent
import com.anibalbastias.android.foreignexchange.presentation.component.DaggerApplicationComponent
import com.anibalbastias.android.foreignexchange.presentation.module.ApplicationModule
import com.bumptech.glide.request.target.ViewTarget

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
        var isTest: Boolean? = false
    }

    override fun onCreate() {
        super.onCreate()
        appComponent().inject(this)
        context = this
        appContext = this

        // Glide Tag
        ViewTarget.setTagId(R.id.glide_tag)
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