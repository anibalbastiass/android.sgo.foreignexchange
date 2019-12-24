package com.anibalbastias.android.foreignexchange.domain.base.interactor

import com.anibalbastias.android.foreignexchange.domain.base.executor.APIPostExecutionThread
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<T, in Params> constructor(
    private val postExecutionThread: APIPostExecutionThread
) : BaseUseCase<T>() {

    protected abstract fun buildUseCaseObservable(params: Params? = null): Single<T>

    open fun execute(
        singleObserver: DisposableSingleObserver<T>,
        params: Params? = null
    ) {
        val single = this.buildUseCaseObservable(params).subscribeOn(Schedulers.io()).observeOn(
            postExecutionThread.scheduler
        ) as Single<T>
        add(single.subscribeWith(singleObserver))
    }
}