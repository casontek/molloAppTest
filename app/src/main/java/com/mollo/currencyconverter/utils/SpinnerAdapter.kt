package com.mollo.currencyconverter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import com.mollo.currencyconverter.R
import com.mollo.currencyconverter.database.CurrencyRate
import com.mollo.currencyconverter.databinding.SpinnerRowLayoutBinding

class SpinnerAdapter(private val c: Context, private val items: List<CurrencyRate>) :
    BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(p0: Int): Any = p0

    override fun getItemId(p0: Int): Long = p0.toLong()

    @SuppressLint("ViewHolder", "UseCompatLoadingForDrawables")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = SpinnerRowLayoutBinding.inflate(LayoutInflater.from(p2?.context), p2, false)
        binding.currencyLabel.text = items[p0].symbol
        when(items[p0].symbol){
            "EUR" -> binding.currencyIcon.setImageDrawable(c.resources.getDrawable(R.drawable.eur))
            "USD" -> binding.currencyIcon.setImageDrawable(c.resources.getDrawable(R.drawable.usd))
            "CAD" -> binding.currencyIcon.setImageDrawable(c.resources.getDrawable(R.drawable.cad))
            "ARS" -> binding.currencyIcon.setImageDrawable(c.resources.getDrawable(R.drawable.ars))
            "AFN" -> binding.currencyIcon.setImageDrawable(c.resources.getDrawable(R.drawable.afn))
            "JPY" -> binding.currencyIcon.setImageDrawable(c.resources.getDrawable(R.drawable.jpy))
            "NGN" -> binding.currencyIcon.setImageDrawable(c.resources.getDrawable(R.drawable.ngn))
        }
        return binding.root
    }

}


