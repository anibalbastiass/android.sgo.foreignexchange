package com.anibalbastias.android.foreignexchange.base.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anibalbastias.android.foreignexchange.base.navigation.BaseNavigationListener
import com.anibalbastias.android.foreignexchange.base.navigation.event.Event
import javax.inject.Inject

/**
 * Created by anibalbastias on 2019-09-08.
 */

class NavBaseViewModel @Inject constructor() : BaseViewModel() {

    //region Navigation
    private val newDestination = MutableLiveData<Pair<Event<Int?>, BaseNavigationListener?>>()

    fun getNewDestination(): LiveData<Pair<Event<Int?>, BaseNavigationListener?>> {
        return newDestination
    }

    fun setNewDestination(destinationId: Int?) {
        newDestination.value = Pair(Event(destinationId), null)
    }

    fun setNewDestination(destinationId: Int?, callback: BaseNavigationListener? = null) {
        newDestination.value = Pair(Event(destinationId), callback)
    }

    fun setNavigateUp() {
        newDestination.value = Pair(Event(-1), null)
    }
    //endregion

}