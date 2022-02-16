package com.mollo.currencyconverter.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
public interface RateDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRate(rateList: List<CurrencyRate>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRate(rate: CurrencyRate?)

    @Query("SELECT * FROM CurrencyRate ORDER BY symbol")
    suspend fun getAllSymbol(): List<CurrencyRate>

    @Query("SELECT rate FROM CurrencyRate WHERE symbol =:label")
    suspend fun getRate(label: String?): Double
}
