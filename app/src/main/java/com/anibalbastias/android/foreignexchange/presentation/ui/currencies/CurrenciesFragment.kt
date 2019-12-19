package com.anibalbastias.android.foreignexchange.presentation.ui.currencies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.SavedStateViewModelFactory
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.base.module.getViewModel
import com.anibalbastias.android.foreignexchange.base.view.BaseModuleFragment
import com.anibalbastias.android.foreignexchange.databinding.FragmentCurrenciesListBinding
import com.anibalbastias.android.foreignexchange.presentation.appComponent
import com.anibalbastias.android.foreignexchange.presentation.getAppContext
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.CurrenciesViewData
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.model.CurrencyItemViewData
import com.anibalbastias.android.foreignexchange.presentation.ui.currencies.viewmodel.CurrenciesViewModel
import com.anibalbastias.android.foreignexchange.presentation.util.*
import com.anibalbastias.android.foreignexchange.presentation.util.adapter.base.BaseBindClickHandler


/**
 * Created by anibalbastias on 2019-11-25.
 */

class CurrenciesFragment : BaseModuleFragment(), BaseBindClickHandler<CurrencyItemViewData> {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_currencies_list

    private lateinit var binding: FragmentCurrenciesListBinding
    private lateinit var currenciesViewModel: CurrenciesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        navBaseViewModel = getViewModel(viewModelFactory)
        currenciesViewModel = getViewModel(viewModelFactory)
        sharedViewModel = activity!!.getViewModel(SavedStateViewModelFactory(getAppContext(), this))
        setHasOptionsMenu(true)
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
        fetchCurrencies()
    }

    private fun fetchCurrencies() {
        currenciesViewModel.apply {
            getLatestCurrenciesLiveData().value?.data?.let {
                setLatestCurrenciesData(it)
            } ?: run {
                isLoading.set(true)
                getLatestCurrenciesData(currencySelected.get()?.title)
            }

            // Set Swipe Refresh Layout
            binding.currenciesListSwipeRefreshLayout?.initSwipe {
                getLatestCurrenciesData(currencySelected.get()?.title)
            }

            // Observe changes for currency selected
            currencySelected.addOnPropertyChanged {
                getLatestCurrenciesData(currencySelected.get()?.title)
            }
        }
    }

    private fun setLatestCurrenciesData(viewData: CurrenciesViewData?) {
        currenciesViewModel.apply {

            // Notify observers
            isLoading.set(false)
            isError.set(false)
            binding.currenciesListSwipeRefreshLayout?.isRefreshing = false

            // Set data
            var currencies = mutableListOf<CurrencyItemViewData>()

            // If haven't items, only add
            if (currencyList.get()?.isEmpty() == true) {
                viewData?.rates?.forEach {
                    currencies.add(getCurrencyItem(it))
                }
            } else {
                currencies = currencyList.get()!!

                val keyList = ArrayList(viewData?.rates?.keys!!)
                val valueList = ArrayList(viewData.rates.values)

                // Match two arrays and compare
                keyList.zip(currencies).mapIndexed { index, pair ->
                    if (pair.first == pair.second.title) {
                        currencies[index].value?.set(getFormattedValue(valueList[index].toString()))
                    }
                }
            }
            currencyList.set(currencies as ArrayList<CurrencyItemViewData>?)

            // Keep position for recyclerView
            binding.currenciesListRecyclerView.paginationForRecyclerScroll(itemPosition)

        }
    }

    private fun initToolbar() {
        binding.currenciesToolbar?.run {
            applyFontForToolbarTitle(activity!!)
            setNoArrowUpToolbar(activity!!)
        }
    }

    private fun initViewModel() {
        // Fetch Latest Currencies
        currenciesViewModel.run {
            implementObserver(getLatestCurrenciesLiveData(),
                successBlock = { viewData -> setLatestCurrenciesData(viewData) },
                errorBlock = { showErrorView() })
        }
    }

    private fun showErrorView() {
        currenciesViewModel.isError.set(true)
    }

    override fun onClickView(view: View, item: CurrencyItemViewData) {
        activity?.toast(item.title)
    }

}