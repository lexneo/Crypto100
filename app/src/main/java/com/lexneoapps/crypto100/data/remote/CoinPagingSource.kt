package com.lexneoapps.crypto100.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lexneoapps.crypto100.api.CoinMarketApi
import com.lexneoapps.crypto100.data.remote.model.Data
import com.lexneoapps.crypto100.other.NETWORK_PAGE_SIZE
import com.lexneoapps.crypto100.other.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class CoinPagingSource(
    private val api: CoinMarketApi
) : PagingSource<Int, Data>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val data = api.getCryptoData(position, params.loadSize).data
            LoadResult.Page(
                data = data,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position > 10) null else position + (params.loadSize / NETWORK_PAGE_SIZE)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}