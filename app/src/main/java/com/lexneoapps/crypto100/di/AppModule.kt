package com.lexneoapps.crypto100.di

import com.google.gson.GsonBuilder
import com.lexneoapps.crypto100.api.CoinMarketApi
import com.lexneoapps.crypto100.other.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCoinMarketApi(): CoinMarketApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(CoinMarketApi::class.java)
    }
}