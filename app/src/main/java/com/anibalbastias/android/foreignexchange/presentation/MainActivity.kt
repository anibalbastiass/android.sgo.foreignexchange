package com.anibalbastias.android.foreignexchange.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anibalbastias.android.foreignexchange.R

/**
 * Created by anibalbastias on 2019-11-25.
 */

class MainActivity : AppCompatActivity() {

    var layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        appComponent().inject(this)
    }
}
