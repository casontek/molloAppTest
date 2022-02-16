package com.mollo.currencyconverter.network

import com.mollo.currencyconverter.utils.Constants
import com.mollo.currencyconverter.models.CurrencyData
import com.mollo.currencyconverter.models.RateChanges
import retrofit2.Response
import javax.inject.Inject


class networkRepository @Inject constructor(private val restService: RestService) {
    suspend fun getCurrencyData(base: String) : Response<CurrencyData> =
        restService.getCurrencyData(base, Constants.api_key, Constants.defaultSymbols)

    suspend fun getSymbolRateChanges(base: String, startDate: String, endDate: String) : Response<RateChanges> =
        restService.getCurrencyChanges(base, Constants.api_key, Constants.defaultSymbols, startDate, endDate)
}