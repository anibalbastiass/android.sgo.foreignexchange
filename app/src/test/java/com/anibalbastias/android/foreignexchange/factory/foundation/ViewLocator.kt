package com.anibalbastias.android.foreignexchange.factory.foundation

import android.app.Activity
import android.widget.TextView

object ViewLocator {

    fun getTextViewFromId(activity: Activity, viewId: Int): TextView? {
        return activity.findViewById(viewId)
    }

}