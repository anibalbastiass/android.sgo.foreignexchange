package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrenciesViewData(

    val rates: HashMap<String, Double>? = null,
    val base: String? = null,
    val date: String? = null

) : Parcelable