package com.anibalbastias.android.foreignexchange.presentation.util

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.filter.FilterWordListener
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.base.BaseBindClickHandler
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.base.SingleLayoutBindRecyclerAdapter

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    imageUrl?.let {
        loadImageWithPlaceholderCenterCrop(imageUrl)
    }
}

@BindingAdapter("setImageUrlFull")
fun ImageView.setImageUrlFull(imageUrl: String?) {
    imageUrl?.let {
        loadImage(imageUrl)
    }
}

@BindingAdapter(
    value = ["loadAdapterData", "loadAdapterLayout", "loadAdapterListener", "filter"],
    requireAll = false
)
fun <T> RecyclerView.loadAdapterData(
    list: MutableList<T>?,
    layout: Int?,
    callback: BaseBindClickHandler<T>? = null,
    filter: FilterWordListener<T>? = null
) {
    context?.run {
        layout?.let { layoutId ->
            layoutManager = GridLayoutManager(context, 2)
            val singleAdapter = SingleLayoutBindRecyclerAdapter(layoutId, list, callback, filter)
            adapter = singleAdapter
            runLayoutAnimation()
        }
    }
}
