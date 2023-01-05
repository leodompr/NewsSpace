package com.uruklabs.newsspace.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.uruklabs.newsspace.R

/**
 * A MainActivity serve essencialmente para hospedar os
 * fragmentos.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}