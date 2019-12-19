package com.anibalbastias.android.foreignexchange.base.subscriber

import android.content.Context
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by anibal.bastias on 26, June, 2019
 *
 * Transform data error to API error view data
 */
abstract class APIExceptionHandlerSubscriber<T>(val context: Context?) : DisposableSingleObserver<T>() {

    abstract fun onAPIError(apiErrorViewData: String?)

    abstract override fun onSuccess(t: T)

    override fun onError(t: Throwable) {
        onAPIError(t.localizedMessage)
    }
}