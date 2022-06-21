package com.lexneoapps.crypto100.api

import com.lexneoapps.crypto100.data.remote.response.NetworkResponse
import com.lexneoapps.crypto100.other.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CoinMarketApi {


    @Headers("X-CMC_PRO_API_KEY: $API_KEY")
    @GET("v1/cryptocurrency/listings/latest?")
    suspend fun getCryptoData(
        @Query("start") start: Int = 1,
        @Query("limit") limit: Int = 10
    ): NetworkResponse
}