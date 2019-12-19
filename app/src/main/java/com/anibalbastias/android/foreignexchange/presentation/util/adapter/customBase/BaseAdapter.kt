package com.anibalbastias.android.foreignexchange.presentation.util.adapter.customBase

import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var isLoadingAdded = false
    var hasHeader = true
    abstract var items: MutableList<T?>

    companion object {
        const val HEADER_TYPE = 0
        const val ITEM_TYPE = 1
        const val LOADING_TYPE = 2
    }

    val isEmpty: Boolean
        get() = itemCount == 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            HEADER_TYPE -> viewHolder = createHeaderViewHolder(parent)
            ITEM_TYPE -> viewHolder = createItemViewHolder(parent)
            LOADING_TYPE -> viewHolder = createLoadingViewHolder(parent)
            else -> {
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEADER_TYPE -> bindHeaderViewHolder(viewHolder)
            ITEM_TYPE -> bindItemViewHolder(viewHolder, position)
            LOADING_TYPE -> bindFooterViewHolder(viewHolder)
            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // region Abstract Methods
    protected abstract fun createHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder?

    protected abstract fun createItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder?

    protected abstract fun createLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder?

    protected abstract fun bindHeaderViewHolder(viewHolder: RecyclerView.ViewHolder)

    protected abstract fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)

    protected abstract fun bindFooterViewHolder(viewHolder: RecyclerView.ViewHolder)

    abstract fun addLoadingFooter()

    fun getItem(position: Int): T? {
        return if (position > items.size - 1) {
            null
        } else {
            items[position]
        }

    }

    fun insert(item: T, position: Int) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    fun add(item: T) {
        items.add(item)
        Handler().post {
            notifyItemInserted(items.size - 1)
        }
    }

    fun addAll(items: List<T?>) {
        for (item in items) {
            item?.let {
                add(it)
            }

        }
    }

    private fun remove(item: T?) {
        val position = items.indexOf(item)
        if (position > -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            remove(getItem(0))
        }
    }

    fun isLastPosition(position: Int): Boolean {
        return position == items.size - 1
    }

    fun isFirstPosition(position: Int): Boolean {
        return position == 0
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        if (items.size > 0) {
            val position = items.size - 1
            val item = getItem(position)

            if (item != null) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }

    }

    class PagingLoaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

