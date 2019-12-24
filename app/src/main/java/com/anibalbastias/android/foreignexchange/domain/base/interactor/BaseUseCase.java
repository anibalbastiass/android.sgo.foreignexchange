package com.anibalbastias.android.foreignexchange.domain.base.interactor;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Pablo Araya @pablo on 7/19/17
 */

public abstract class BaseUseCase<T> {

    protected final CompositeDisposable compositeDisposable;

    public BaseUseCase() {
        compositeDisposable = new CompositeDisposable();
    }

    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void remove(Disposable disposable) {
        if (disposable != null) compositeDisposable.remove(disposable);
    }

    public void add(Disposable disposable) {
        if (disposable != null) compositeDisposable.add(disposable);
    }
}
