package com.mollo.currencyconverter.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.mollo.currencyconverter.database.CurrencyRate
import com.mollo.currencyconverter.databinding.ActivityMainBinding
import com.mollo.currencyconverter.models.CurrencyData
import com.mollo.currencyconverter.models.RateChanges
import com.mollo.currencyconverter.utils.Constants
import com.mollo.currencyconverter.utils.MolloViewModel
import com.mollo.currencyconverter.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val myModel: MolloViewModel by viewModels()
    private lateinit var data: CurrencyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //load the initial currency data using EUR as currency base
        myModel.viewModelScope.launch {
            binding.btnConvert.isEnabled = false
            myModel.getCurrencyData(Constants.currencyDefaultBase).collect {
                when(it.status){
                    Status.Success ->{
                        data = it.data!!
                        if(data.rates.isNotEmpty()){
                            //save data to local database
                            saveData(data.rates)
                        }
                        //enables convert button
                        binding.btnConvert.isEnabled = true
                    }
                    Status.Error ->{
                        var msg: String = "Network communication problems"
                        if(it.code == 200){
                            msg = it.data?.error?.type!!
                        }
                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //load EUR symbol changes
            myModel.getRateChanges(Constants.currencyDefaultBase, "2012-05-01", "2012-05-25").collect {
                when(it.status){
                    Status.Success ->{
                        val rateChages = it.data!!
                        if(data.rates.isNotEmpty()){
                            //save data to local database
                            displaysGraph(rateChages)
                        }
                    }
                    Status.Error ->{
                        var msg: String = "Network communication problems"
                        if(it.code == 200){
                            msg = it.data?.error?.type!!
                        }
                        Toast.makeText(baseContext, "$msg for graph data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnConvert.setOnClickListener {
            val intent = Intent(baseContext, ConvertHome::class.java)
            startActivity(intent)
        }
    }

    private fun saveData(rates: Map<String, Double>) {
        for (rate in rates){
            val rt = CurrencyRate(rate.key, rate.value)
            save(rt)
        }
    }

    private fun save(rt: CurrencyRate) {
        myModel.viewModelScope.launch {
                myModel.saveRate(rt)
        }
    }

    private fun displaysGraph(rateChages: RateChanges) {
        val maps = rateChages.rates
        var count = 0.0
        for(map in maps){
            val innerMap = map.value
            val date = map.key
            for(m in innerMap){
            }
            count += 1
        }
    }

}