package com.mollo.currencyconverter.utils

data class Result<out T>(val status: Status, val code: Int, val data: T?) {

    companion object{
        fun <T> success(code: Int, data: T?) : Result<T> {
            return Result(Status.Success, code, data)
        }

        fun <T> error(code: Int, data: T?) : Result<T> {

            return Result(Status.Error, code, data)
        }

        fun <T> loading() : Result<T> {
            return Result(Status.Loading, 0, null)
        }
    }
}