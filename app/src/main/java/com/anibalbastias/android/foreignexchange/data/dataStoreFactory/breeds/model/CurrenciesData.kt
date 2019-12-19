package com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.model

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.common.TypeData
import com.google.gson.annotations.SerializedName

data class CurrenciesData(

    @field:SerializedName("rates")
    val rates: HashMap<String, Double>? = null,

    @field:SerializedName("base")
    val base: String? = null,

    @field:SerializedName("date")
    val date: String? = null

) : TypeData()