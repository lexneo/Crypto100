package com.lexneoapps.crypto100.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.lexneoapps.crypto100.api.CoinMarketApi
import com.lexneoapps.crypto100.data.remote.CoinPagingSource
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val api: CoinMarketApi
) {
    fun getCoins() =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 30
            ),
            pagingSourceFactory = { CoinPagingSource(api) }
        ).liveData
}