package com.mollo.currencyconverter.modules

import com.mollo.currencyconverter.utils.Constants
import com.mollo.currencyconverter.network.RestService
import com.mollo.currencyconverter.network.networkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesOkHttpClient() : OkHttpClient = OkHttpClient
        .Builder()
        .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) : Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun providesRestService(retrofit: Retrofit) : RestService = retrofit
        .create(RestService::class.java)

    @Singleton
    @Provides
    fun providesNetworkRepository(restService: RestService) = networkRepository(restService)

}