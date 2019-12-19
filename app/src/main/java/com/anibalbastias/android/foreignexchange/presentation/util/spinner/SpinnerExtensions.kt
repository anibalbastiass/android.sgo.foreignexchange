package com.anibalbastias.android.foreignexchange.presentation.util.spinner

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.InverseBindingListener
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.CurrencyItemViewData
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.spinner.FlagSpinnerAdapter

/**
 * Created by Anibal Bastias Soto on 2019-12-17.
 */

object SpinnerExtensions {

    /**
     * set spinner entries
     */
    fun Spinner.setDefaultSpinnerEntries(entries: List<Any>?) {
        if (entries != null) {
            val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, entries)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter = arrayAdapter
        }
    }

    fun Spinner.setFlagsSpinnerEntries(currencies: List<CurrencyItemViewData>?) {
        currencies?.let {
            adapter = FlagSpinnerAdapter(currencies)
        }
    }

    /**
     * set spinner onItemSelectedListener listener
     */
    fun Spinner.setSpinnerItemSelectedListener(listener: ItemSelectedListener?) {
        if (listener == null) {
            onItemSelectedListener = null
        } else {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (tag != position) {
                        listener.onItemSelected(parent.getItemAtPosition(position))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    /**
     * set spinner onItemSelectedListener listener
     */
    fun Spinner.setSpinnerInverseBindingListener(listener: InverseBindingListener?) {
        if (listener == null) {
            onItemSelectedListener = null
        } else {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (tag != position) {
                        listener.onChange()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    /**
     * set spinner value
     */
    fun Spinner.setFlagSpinnerValue(value: CurrencyItemViewData?) {
        if (adapter != null) {
            val position = (adapter as FlagSpinnerAdapter).getPosition(value)
            setSelection(position, false)
            tag = position
        }
    }

    fun Spinner.setDefaultSpinnerValue(value: Any?) {
        if (adapter != null) {
            val position = (adapter as ArrayAdapter<Any>).getPosition(value)
            setSelection(position, false)
            tag = position
        }
    }

    /**
     * get spinner value
     */
    fun Spinner.getSpinnerValue(): Any? {
        return selectedItem
    }

    interface ItemSelectedListener {
        fun onItemSelected(item: Any)
    }
}