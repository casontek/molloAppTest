package com.mollo.currencyconverter.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyRate(@PrimaryKey var symbol: String, var rate: Double)
