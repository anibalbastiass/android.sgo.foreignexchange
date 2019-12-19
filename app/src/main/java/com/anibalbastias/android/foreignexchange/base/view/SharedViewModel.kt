package com.anibalbastias.android.foreignexchange.base.view

import androidx.lifecycle.SavedStateHandle
import javax.inject.Inject

/**
 * Created by anibalbastias on 2019-09-08.
 */

class SharedViewModel @Inject constructor(state: SavedStateHandle) : BaseViewModel() {

    // Keep the key as a constant
    companion object {
        private const val RESULT_ITEM_KEY = "resultItemKey"
        private const val FULL_IMAGE_KEY = "fullImageKey"
    }

    private val savedStateHandle = state

}