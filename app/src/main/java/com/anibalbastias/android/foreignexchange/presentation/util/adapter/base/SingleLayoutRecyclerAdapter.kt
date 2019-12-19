package com.anibalbastias.android.foreignexchange.presentation.util.adapter.base

import android.widget.Filter
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.filter.CustomFilter
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.filter.FilterWordListener

/**
 * Created by anibalbastias on 2019-08-12.
 *
 * Base recycler view adapter with
 * data binding that uses only one layout
 */
class SingleLayoutBindRecyclerAdapter<T>(
    private val layoutId: Int,
    var list: List<T?>?,
    clickHandler: BaseBindClickHandler<T>? = null,
    var callback: FilterWordListener<T>? = null
) : BaseBindRecyclerAdapter<T>(clickHandler) {

    private var filterList: ArrayList<T?>? = null
    private var filter: CustomFilter<T>? = null

    init {
        filterList = list as ArrayList<T?>?
    }

    override fun getLayoutIfForPosition(position: Int): Int {
        return layoutId
    }

    override fun getItemForPosition(position: Int): T {
        return list?.get(position)!!
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun setData(data: List<T>?) {
        list = data
        notifyDataSetChanged()
    }

    public override fun getFilter(): Filter? {
        if (filter == null) {
            filter = CustomFilter(filterList, this, callback)
        }
        return filter
    }
}