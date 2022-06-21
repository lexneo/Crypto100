package com.lexneoapps.crypto100.repository

import com.lexneoapps.crypto100.api.CoinMarketApi
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val api: CoinMarketApi
) {

    suspend fun getData(currentPage: Int) = api.getCryptoData(start = currentPage)

}