package com.mollo.currencyconverter.network

import com.mollo.currencyconverter.models.CurrencyData
import com.mollo.currencyconverter.models.RateChanges
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RestService {
    @GET("api/latest")
    suspend fun getCurrencyData(@Query("base") base: String, @Query("access_key") key: String, @Query("symbols") symbol: String) : Response<CurrencyData>

    @GET("api/timeseries")
    suspend fun getCurrencyChanges(@Query("base") base: String, @Query("access_key") key: String,
                                   @Query("symbols") symbol: String, @Query("start_date") startDate: String,
                                    @Query("end_date") endDate: String) : Response<RateChanges>

}