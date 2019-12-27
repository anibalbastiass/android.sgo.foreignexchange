package com.anibalbastias.android.foreignexchange.presentation.util

import com.anibalbastias.android.foreignexchange.base.module.coroutines.scope.ManagedCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * Created by Anibal Bastias Soto on 2019-12-26.
 */

@ExperimentalCoroutinesApi
class TestScope(override val coroutineContext: CoroutineContext) : ManagedCoroutineScope {

    private val scope = TestCoroutineScope(coroutineContext)

    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch {
            block.invoke(this)
        }
    }
}