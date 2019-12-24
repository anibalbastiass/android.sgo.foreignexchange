package com.anibalbastias.android.foreignexchange.data.foreignexchange

import com.anibalbastias.android.foreignexchange.data.dataStoreFactory.currency.model.RemoteCurrencies
import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiConstants.GET_LATEST_CURRENCIES
import com.anibalbastias.android.foreignexchange.data.foreignexchange.ForeignExchangeApiConstants.KEY_BASE_CURRENCY
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by anibalbastias on 3/03/19.
 */

interface ForeignExchangeApiService {

    //region Breeds
    @GET(GET_LATEST_CURRENCIES)
    fun getLatestCurrencies(@Query(KEY_BASE_CURRENCY) base: String): Single<RemoteCurrencies>
    //endregion

}