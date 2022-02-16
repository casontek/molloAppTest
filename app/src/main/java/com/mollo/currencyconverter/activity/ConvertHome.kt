package com.mollo.currencyconverter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.mollo.currencyconverter.databinding.ActivityConvertHomeBinding
import com.mollo.currencyconverter.utils.MolloViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.mollo.currencyconverter.R
import com.mollo.currencyconverter.database.CurrencyRate
import com.mollo.currencyconverter.utils.Constants
import com.mollo.currencyconverter.utils.SpinnerAdapter
import com.mollo.currencyconverter.utils.Status
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.time.Duration.Companion.hours


@AndroidEntryPoint
class ConvertHome : AppCompatActivity() {
    private lateinit var binding: ActivityConvertHomeBinding
    private val model: MolloViewModel by viewModels()
    private var currencies: List<CurrencyRate> = arrayListOf()
    private var baseCurrency = Constants.currencyDefaultBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConvertHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //load the saved currency data
        model.viewModelScope.launch {
            currencies = model.getSymbols()
            if(currencies.isNotEmpty()){
                renderData()
            }
        }
        //listens to convert button click and spinner selection
        addListeners()

        val date = Date()
        binding.btnMidSisplay.text = "${resources.getString(R.string.mid_market)} ${date.hours}:${date.minutes}"

    }

    private fun renderData() {
        val adapter = SpinnerAdapter(baseContext, currencies)
        binding.currencySelector2.adapter = adapter
        binding.currencySelector1.adapter = adapter
        //sets the current base to EUR
        setBaseCurrency()
    }

    private fun setBaseCurrency(){
        binding.currencySymbol.text = baseCurrency
        binding.currency1.setText("1")
        //sets the base
        var count = 0
        for(currency in currencies){
            if(currency.symbol == baseCurrency){
                binding.currencySelector1.setSelection(count)
            }
            count += 1
        }
    }

    private fun addListeners() {
        binding.btnConvert.setOnClickListener {
            var pos = binding.currencySelector1.selectedItemPosition
            var base = currencies[pos].symbol
            if(base != baseCurrency){
                binding.btnConvert.isEnabled = false
                //reload data
                model.viewModelScope.launch {
                    model.getCurrencyData(base).collect {
                        when(it.status){
                            Status.Success ->{
                                binding.btnConvert.isEnabled = true
                                val data = it.data?.rates
                                if (data != null) {
                                    baseCurrency = base
                                    saveData(data)
                                }
                            }
                            Status.Error ->{
                                binding.btnConvert.isEnabled = true
                                var msg = "Network communication problems"
                                if(it.code == 200){
                                    msg = it.data?.error?.type!!
                                }
                                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }

        //sets the target rate when spinner is selected
        binding.currencySelector2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val pos = binding.currencySelector2.selectedItemPosition
                val label = currencies[pos].symbol
                model.viewModelScope.launch {
                    val rate = model.getSymbolRate(label)
                    binding.currencySymbol2.text = label
                    binding.currency2.setText(rate.toString())
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun saveData(rates: Map<String, Double>) {
        for (rate in rates){
            val rtl = CurrencyRate(rate.key, rate.value)
            currencies.toMutableList().add(rtl)
        }
        Log.d("_&check", "new currency of new base: $currencies")

        model.viewModelScope.launch {
            model.saveRates(currencies)
            setBaseCurrency()
        }
    }

}