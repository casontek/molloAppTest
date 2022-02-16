package com.mollo.currencyconverter.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorInfo(val code: Int, val type: String)
