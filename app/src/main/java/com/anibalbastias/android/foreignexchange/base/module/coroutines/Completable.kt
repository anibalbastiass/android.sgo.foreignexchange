package com.anibalbastias.android.foreignexchange.base.module.coroutines

/**
 * Created by anibal.bastias on 2019-12-26.
 */

sealed class Completable {
    object OnComplete : Completable()
    data class OnError(val throwable: Throwable) : Completable()
    object OnLoading : Completable()
    object OnCancel : Completable()
}