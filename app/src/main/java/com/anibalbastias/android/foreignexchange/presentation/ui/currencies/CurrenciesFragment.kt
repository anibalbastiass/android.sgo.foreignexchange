package com.anibalbastias.android.foreignexchange.presentation.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.module.coroutines.Result
import com.anibalbastias.android.foreignexchange.base.module.getViewModel
import com.anibalbastias.android.foreignexchange.base.view.BaseModuleFragment
import com.anibalbastias.android.foreignexchange.databinding.FragmentCurrenciesListBinding
import com.anibalbastias.android.foreignexchange.presentation.appComponent
import com.anibalbastias.android.foreignexchange.presentation.getAppContext
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencies
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.UiCurrencyItem
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel.CurrenciesViewModel
import com.anibalbastias.android.foreignexchange.presentation.util.*
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.base.BaseBindClickHandler


/**
 * Created by anibalbastias on 2019-11-25.
 */

open class CurrenciesFragment : BaseModuleFragment(), BaseBindClickHandler<UiCurrencyItem> {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_currencies_list

    lateinit var binding: FragmentCurrenciesListBinding
    lateinit var currenciesViewModel: CurrenciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        setHasOptionsMenu(true)
    }

    open fun setupInjection() {
        appComponent().inject(this)
        navBaseViewModel = getViewModel(viewModelFactory)
        currenciesViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavController(this@CurrenciesFragment.view)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentCurrenciesListBinding
        binding.currenciesViewModel = currenciesViewModel
        binding.currenciesItemListener = this
        binding.lifecycleOwner = this

        initToolbar()
        initViewModel()
    }

    private fun initViewModel() {
        // Fetch Latest Currencies
        with(currenciesViewModel) {
            observe(getLatestCurrenciesLiveData(), ::observeCurrencies)
            fetchCurrencies()
        }
    }

    private fun observeCurrencies(result: Result<UiCurrencies>?) {
        when (result) {
            is Result.OnLoading -> currenciesViewModel.isLoading.set(true)
            is Result.OnSuccess -> {
                currenciesViewModel.isLoading.set(false)
                setLatestCurrenciesData(result.value)
            }
            is Result.OnError -> showErrorView()
            else -> showErrorView()
        }
    }

    private fun fetchCurrencies() {
        currenciesViewModel.apply {
            getLatestCurrenciesLiveData().value?.let { result ->
                setLatestCurrenciesData((result as? Result.OnSuccess)?.value)
            } ?: run {
                isLoading.set(true)
                getLatestCurrenciesData()
            }

            // Set Swipe Refresh Layout
            binding.currenciesListSwipeRefreshLayout.initSwipe {
                getLatestCurrenciesData()
            }

            // Observe changes for currency selected
            currencySelected.addOnPropertyChanged {
                getLatestCurrenciesData()
            }
        }
    }

    private fun setLatestCurrenciesData(viewData: UiCurrencies?) {

        binding.currenciesListSwipeRefreshLayout.isRefreshing = false

        currenciesViewModel.apply {
            setLatestCurrenciesUi(viewData)

            // Keep position for recyclerView
            binding.currenciesListRecyclerView.paginationForRecyclerScroll(itemPosition)
        }
    }

    private fun initToolbar() {
        binding.currenciesToolbar.run {
            applyFontForToolbarTitle(activity!!)
            setNoArrowUpToolbar(activity!!)
        }
    }

    private fun showErrorView() {
        currenciesViewModel.isError.set(true)
    }

    override fun onClickView(view: View, item: UiCurrencyItem) {
        activity?.toast(item.title)
    }

}