package com.anibalbastias.android.foreignexchange.presentation.util.adapter.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.anibalbastias.android.foreignexchange.BR


/**
 * Created by anibalbastias on 2019-08-12.
 *
 */
class BaseBindViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : T, clickHandler: BaseBindClickHandler<T>? = null) {

        binding.setVariable(BR.item, item)

        if(clickHandler != null)
            binding.setVariable(BR.clickHandler, clickHandler)

        binding.executePendingBindings()
    }
}