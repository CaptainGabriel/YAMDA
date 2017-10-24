package com.dev.moviedb.mvvm.components.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.moviedb.mvvm.components.front.tabs_layout.TabOptionsActivity

/**
 * Presents the splash screen with the app logo.
 *
 * @author PeteGabriel on 10/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
class SplashActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, TabOptionsActivity::class.java))
    }
}