package com.mollo.currencyconverter.database

import javax.inject.Inject


class localRepository @Inject constructor(private val rateDAO: RateDAO){
    suspend fun saveRates(rates: List<CurrencyRate>)  = rateDAO.insertAllRate(rates)

    suspend fun saveRate(rate: CurrencyRate) = rateDAO.insertRate(rate)

    suspend fun rate(label: String) : Double = rateDAO.getRate(label)

    suspend fun symbols() : List<CurrencyRate> = rateDAO.getAllSymbol()
}