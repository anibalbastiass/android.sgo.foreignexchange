package com.anibalbastias.android.foreignexchange.data.foreignexchange

import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiConstants.GET_LATEST_CURRENCIES
import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiConstants.KEY_BASE_CURRENCY
import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.breeds.model.CurrenciesData
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * Created by anibalbastias on 3/03/19.
 */

interface ForeignExchangeApiService {

    //region Breeds
    @GET(GET_LATEST_CURRENCIES)
    fun getLatestCurrencies(@Query(KEY_BASE_CURRENCY) base: String): Flowable<CurrenciesData>
    //endregion

}