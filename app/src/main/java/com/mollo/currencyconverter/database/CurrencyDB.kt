package com.mollo.currencyconverter.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyRate::class], version = 1, exportSchema = false)
abstract class CurrencyDB : RoomDatabase() {
    abstract fun coverterDao() : RateDAO
}