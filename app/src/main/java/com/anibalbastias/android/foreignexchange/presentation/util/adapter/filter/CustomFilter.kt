package com.anibalbastias.android.foreignexchange.presentation.util.adapter.filter

import android.widget.Filter
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.base.SingleLayoutBindRecyclerAdapter
import java.util.*


/**
 * Created by anibalbastias on 01/12/2019.
 */

class CustomFilter<T>(
    var filterList: MutableList<T?>?,
    var adapter: SingleLayoutBindRecyclerAdapter<T>,
    var callback: FilterWordListener<T>?
) : Filter() {

    override fun performFiltering(constraint: CharSequence): FilterResults {

        var word: CharSequence? = constraint
        val results = FilterResults()

        if (word != null && word.isNotEmpty()) {
            word = word.toString()

            val filteredItems: ArrayList<T> = arrayListOf()

            for (i in filterList?.indices!!) {
                filterList!![i]?.let { callback?.onFilterByWord(word, it, filteredItems) }
            }

            results.count = filteredItems.size
            results.values = filteredItems
        } else {
            results.count = filterList?.size ?: 0
            results.values = filterList
        }
        return results
    }

    override fun publishResults(
        constraint: CharSequence,
        results: FilterResults
    ) {
        adapter.list = results.values as ArrayList<T>
        adapter.notifyDataSetChanged()
    }

}

interface FilterWordListener<T> {
    fun onFilterByWord(word: String, selectedItem: T, filteredItems: ArrayList<T>)
}