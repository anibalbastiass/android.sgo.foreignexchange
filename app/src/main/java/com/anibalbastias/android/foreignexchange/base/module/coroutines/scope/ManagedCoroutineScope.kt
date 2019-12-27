package com.anibalbastias.android.foreignexchange.base.module.coroutines.scope

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * Created by Anibal Bastias Soto on 2019-12-26.
 */

interface ManagedCoroutineScope : CoroutineScope {
    abstract fun launch(block: suspend CoroutineScope.() -> Unit): Job
}