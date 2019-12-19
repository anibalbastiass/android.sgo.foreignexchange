package com.anibalbastias.android.foreignexchange.base.subscriber

import android.content.Context
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by anibal.bastias on 26, June, 2019
 *
 * Transform data error to API error view data
 */
abstract class APIExceptionHandlerSubscriber<T>(val context: Context?) : DisposableSubscriber<T>() {

    abstract fun onAPIError(apiErrorViewData: String?)
    abstract override fun onNext(t: T)

    override fun onComplete(){}

    override fun onError(t: Throwable?) {
        onAPIError(t?.localizedMessage)
    }
}