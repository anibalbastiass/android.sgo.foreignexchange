package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UiCurrencies(

    val rates: HashMap<String, Double>? = null,
    val base: String? = null,
    val date: String? = null

) : Parcelable