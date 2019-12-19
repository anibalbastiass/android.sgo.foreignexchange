package com.anibalbastias.android.foreignexchange.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.view.Resource
import com.anibalbastias.android.foreignexchange.base.view.ResourceState
import com.anibalbastias.android.foreignexchange.presentation.GlideApp
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.CurrencyItemViewData
import com.anibalbastias.android.foreignexchange.presentation.util.spinner.SpinnerExtensions
import com.anibalbastias.android.foreignexchange.presentation.util.spinner.SpinnerExtensions.setFlagSpinnerValue
import com.anibalbastias.android.foreignexchange.presentation.util.spinner.SpinnerExtensions.setFlagsSpinnerEntries
import com.anibalbastias.android.foreignexchange.presentation.util.spinner.SpinnerExtensions.setSpinnerItemSelectedListener
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.*

fun <T> LiveData<T>.initObserver(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit) {
    try {
        removeObservers(lifecycleOwner)
        observe(lifecycleOwner, Observer<T> { t -> observer.invoke(t!!) })
    } catch (e: KotlinNullPointerException) {
        e.printStackTrace()
    }

}

fun <T> LiveData<T>.initObserverForever(observer: Observer<T>) {
    removeObserver(observer)
    observeForever { t -> observer.onChanged(t) }
}

@SuppressLint("ShowToast")
fun Activity.getToastTypeFaced(text: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    val toast = Toast.makeText(this, text, duration)
    val typeface = ResourcesCompat.getFont(applicationContext!!, R.font.opensans_regular)
    val toastLayout = toast.view as? LinearLayout
    val toastTV = (toastLayout?.getChildAt(0) as? TextView)
    toastTV?.typeface = typeface
    return toast
}

fun Activity.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) =
    text?.let {
        getToastTypeFaced(text, duration).show()
    }

@SuppressLint("SimpleDateFormat")
fun getTs(): String {
    val tsLong = Date().time / 1000
    return tsLong.toString()
}

fun String.Companion.empty() = ""

fun ImageView.loadImageWithoutPlaceholder(url: String) =
    GlideApp.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(true)
        .into(this)

fun ImageView.loadImage(url: String) =
    GlideApp.with(context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(true)
        .into(this)

fun ImageView.loadImageWithPlaceholderCenterCrop(url: String) =
    GlideApp.with(context)
        .load(url)
        .centerInside()
        .placeholder(R.drawable.ic_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(false)
        .into(this)

fun isTablet(context: Context): Boolean = try {
    context.resources.getBoolean(R.bool.isTablet)
} catch (ex: Exception) {
    false
}

fun SwipeRefreshLayout.initSwipe(onSwipeUnit: (() -> Unit)?) {
    this.setColorSchemeColors(ContextCompat.getColor(context, R.color.primaryColor))
    this.setOnRefreshListener { onSwipeUnit?.invoke() }
}

/**
 * Implements a custom observer
 * to a MutableLiveData object
 */
fun <T> Fragment.implementObserver(
    mutableLiveData: MutableLiveData<Resource<T>>,
    successBlock: (T) -> Unit = {},
    loadingBlock: () -> Unit = {},
    errorBlock: (String?) -> Unit = {},
    defaultBlock: () -> Unit = {},
    codeBlock: () -> Unit = {},
    hideLoadingBlock: () -> Unit = {}
) {

    handleObserver(
        mutableLiveData,
        defaultBlock,
        successBlock,
        loadingBlock,
        errorBlock,
        codeBlock,
        hideLoadingBlock
    )
}

private fun <T> Fragment.handleObserver(
    mutableLiveData: MutableLiveData<Resource<T>>,
    defaultBlock: () -> Unit,
    successBlock: (T) -> Unit,
    loadingBlock: () -> Unit,
    errorBlock: (String?) -> Unit,
    codeBlock: () -> Unit,
    hideLoadingBlock: () -> Unit = {}
) {
    mutableLiveData.initObserver(this) {
        handleStateObservers(
            codeBlock,
            it,
            defaultBlock,
            successBlock,
            loadingBlock,
            errorBlock,
            hideLoadingBlock
        )
    }
}

private fun <T> handleStateObservers(
    codeBlock: () -> Unit,
    it: Resource<T>?,
    defaultBlock: () -> Unit,
    successBlock: (T) -> Unit,
    loadingBlock: () -> Unit,
    errorBlock: (String?) -> Unit,
    hideLoadingBlock: () -> Unit = {}
) {
    codeBlock()

    when (it?.status) {
        ResourceState.DEFAULT -> defaultBlock()
        ResourceState.SUCCESS -> {
            hideLoadingBlock()
            successBlock(it.data!!)
        }
        ResourceState.LOADING -> loadingBlock()
        ResourceState.ERROR -> {
            hideLoadingBlock()
            errorBlock(it.message)
        }
        else -> {
        }
    }
}

fun <T : androidx.databinding.Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
    addOnPropertyChangedCallback(
        object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                sender: androidx.databinding.Observable?,
                propertyId: Int
            ) {
                return callback(sender as T)
            }
        })


fun RecyclerView.runLayoutAnimation() {
    layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}

fun RecyclerView.paginationForRecyclerScroll(itemPosition: ObservableInt) {
    addOnScrollListener(object :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            itemPosition.set(
                (layoutManager as
                        GridLayoutManager).findFirstVisibleItemPosition()
            )
        }
    })
    scrollToPosition(itemPosition.get())
}

fun getFlagUrlByBase(base: String): String =
    "https://www.countryflags.io/${base.substring(0, 2).toLowerCase()}/flat/64.png"

@BindingAdapter("entries")
fun Spinner.setEntries(entries: List<CurrencyItemViewData>?) {
    entries?.let {
        setFlagsSpinnerEntries(it)
    }
}

@BindingAdapter("onItemSelected")
fun Spinner.setItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
    itemSelectedListener?.let {
        setSpinnerItemSelectedListener(it)
    }
}

@BindingAdapter("newValue")
fun Spinner.setNewValue(newValue: CurrencyItemViewData?) {
    newValue?.let {
        setFlagSpinnerValue(it)
    }
}