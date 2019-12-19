package com.anibalbastias.android.foreignexchange.presentation.ui.entry

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.anibalbastias.android.foreignexchange.R
import com.anibalbastias.android.foreignexchange.presentation.appComponent
import com.anibalbastias.android.foreignexchange.base.view.BaseModuleFragment
import kotlinx.android.synthetic.main.fragment_entry.*

/**
 * Created by anibalbastias on 2019-09-07.
 */

class EntryFragment : BaseModuleFragment() {

    override fun tagName(): String = this::class.java.simpleName
    override fun layoutId(): Int = R.layout.fragment_entry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        setHasOptionsMenu(false)
    }

    companion object {

        fun newInstance(): EntryFragment {
            val args = Bundle()
            val fragment = EntryFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myNavHostFragment: NavHostFragment = nav_host_fragment as NavHostFragment
        val inflater = myNavHostFragment.navController.navInflater

        val graph = inflater.inflate(R.navigation.foreign_exchange_nav_graph)
        myNavHostFragment.navController.graph = graph
    }
}