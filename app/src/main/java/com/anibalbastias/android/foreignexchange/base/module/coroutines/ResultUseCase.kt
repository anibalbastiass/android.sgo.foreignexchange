package com.anibalbastias.android.foreignexchange.base.module.coroutines

import com.anibalbastias.android.foreignexchange.presentation.util.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Jmunoz on 2019-10-31.
 */

abstract class ResultUseCase<P, L>(
    override val backgroundContext: CoroutineContext,
    override val foregroundContext: CoroutineContext
) : BaseUseCase<P, LiveResult<L>>(
    backgroundContext, foregroundContext
) {
    protected abstract suspend fun executeOnBackground(params: P): L?

    override fun execute(liveData: LiveResult<L>, params: P) {
        CoroutineScope(foregroundContext + newJob()).launch {
            liveData.postLoading()

            runCatching {
                withContext(backgroundContext) { executeOnBackground(params)!! }
            }.onSuccess { response ->
                liveData.postSuccess(response)
            }.onFailure { throwable ->
                when (throwable) {
                    is CancellationException -> liveData.postCancel()
                    is NullPointerException -> liveData.postEmpty()
                    else -> liveData.postThrowable(throwable)
                }
            }
        }
    }
}