package com.anibalbastias.android.foreignexchange.presentation.ui.currencies.spinner

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.presentation.context
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencyItem
import com.anibalbastias.android.foreignexchange.presentation.util.empty
import com.anibalbastias.android.foreignexchange.presentation.util.loadImage


/**
 * Created by Anibal Bastias Soto on 2019-12-18.
 */

class FlagSpinnerAdapter(var currencies: List<UiCurrencyItem>) : BaseAdapter() {

    override fun getCount(): Int {
        return currencies.size
    }

    fun getPosition(value: UiCurrencyItem?): Int = currencies.indexOf(value)

    override fun getItem(i: Int): Any? {
        return currencies[i]
    }

    override fun getItemId(i: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = View.inflate(context, R.layout.view_cell_currency_item_spinner, null)
        val names = view.findViewById(R.id.currenciesSpinnerTitle) as TextView
        val icon = view.findViewById(R.id.currenciesSpinnerImageView) as ImageView
        icon.loadImage(currencies[position].imageUrl ?: String.empty())
        names.text = currencies[position].title
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        view = View.inflate(context, R.layout.view_cell_currency_item_spinner, null)
        val names = view.findViewById(R.id.currenciesSpinnerTitle) as TextView
        val icon = view.findViewById(R.id.currenciesSpinnerImageView) as ImageView
        icon.loadImage(currencies[position].imageUrl ?: String.empty())
        names.text = currencies[position].title
        return view
    }
}