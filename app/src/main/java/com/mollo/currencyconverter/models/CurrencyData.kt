package com.mollo.currencyconverter.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyData(val success: Boolean, val timestamp: Long, val base: String,
            val date: String, val rates: Map<String, Double>, val error: ErrorInfo?)
