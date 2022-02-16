package com.mollo.currencyconverter.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mollo.currencyconverter.database.CurrencyRate
import com.mollo.currencyconverter.database.localRepository
import com.mollo.currencyconverter.models.CurrencyData
import com.mollo.currencyconverter.models.RateChanges
import com.mollo.currencyconverter.network.networkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MolloViewModel @Inject constructor(private val repository: networkRepository, private val localRepository: localRepository) : ViewModel() {

    fun getCurrencyData(base: String) : Flow<Result<CurrencyData>> = flow {
        emit(Result.loading())
        repository.getCurrencyData(base).let {
            if(it.isSuccessful){
                if(it.body()?.success == true){
                    Log.d("_&check", "the data returned: ${it.body()}")
                    emit(Result.success(it.code(), it.body()))
                }
                else{
                    Log.d("_&check", "the data returned: ${it.body()}")
                    emit(Result.error(it.code(), it.body()))
                }
            }
            else{
                Log.d("_&check", "the error response: ${it.message()}")
                emit(Result.error<CurrencyData>(it.code(), null))
            }
        }
    }

    fun saveRates(rates: List<CurrencyRate>) = viewModelScope.launch {
        localRepository.saveRates(rates)
    }

    fun saveRate(rate: CurrencyRate) = viewModelScope.launch {
        localRepository.saveRate(rate)
    }

    fun getRateChanges(symbol: String, startDate: String, endDate: String) : Flow<Result<RateChanges>> = flow {
        emit(Result.loading())
        repository.getSymbolRateChanges( symbol, startDate, endDate).let {
            if(it.isSuccessful){
                if(it.body()?.success == true){
                    Log.d("_&check", "the data returned: ${it.body()}")
                    emit(Result.success(it.code(), it.body()))
                }
                else{
                    Log.d("_&check", "the data returned: ${it.body()}")
                    emit(Result.error(it.code(), it.body()))
                }
            }
            else{
                Log.d("_&check", "the error response: ${it.message()}")
                emit(Result.error<RateChanges>(it.code(), null))
            }
        }
    }

    suspend fun getSymbols() : List<CurrencyRate> = localRepository.symbols()

    suspend fun getSymbolRate(symbol: String) : Double = localRepository.rate(symbol)

}