package com.anibalbastias.android.foreignexchange.base.subscriber

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.anibalbastias.android.foreignexchange.base.view.Resource
import com.anibalbastias.android.foreignexchange.base.view.ResourceState

/**
 * Created by anibal.bastias on 26, June, 2019
 *
 * Generic base subscriber class.
 * @param context       Context to use optionally in resources or related functionality
 * @param mapper        Mapper to map corresponding data to view data
 * @param liveData      Mutable live data to work with resource states
 * @param isLoading     Optional ObservableBoolean to work most often with databinding in layout
 */
class VoidResponseAPISubscriber<D>(context: Context?,
                                   val liveData: MutableLiveData<Resource<String>>,
                                   val isLoading: ObservableBoolean? = null,
                                   val isError: ObservableBoolean? = null) : APIExceptionHandlerSubscriber<D>(context) {

    override fun onComplete() {

        isLoading?.set(false)
        liveData.postValue(Resource(ResourceState.SUCCESS, "ok", null))
    }

    override fun onNext(t: D) {
    }

    override fun onAPIError(apiErrorViewData: String?) {
        isLoading?.set(false)
        isError?.set(true)
        liveData.postValue(Resource(ResourceState.ERROR, null, message = apiErrorViewData))
    }
}