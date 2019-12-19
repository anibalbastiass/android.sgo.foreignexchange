package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model

import android.os.Parcelable
import androidx.databinding.ObservableField
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyItemViewData(

    var title: String? = "HRK",
    val imageUrl: String? = String.empty(),
    val defaultValue: ObservableField<String>? = ObservableField(String.empty()),
    val value: ObservableField<String>? = ObservableField(String.empty()),
    val currency: ObservableField<String>? = ObservableField(String.empty())

) : Parcelable