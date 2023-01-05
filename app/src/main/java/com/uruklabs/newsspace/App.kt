package com.uruklabs.newsspace

import android.app.Application
import com.uruklabs.newsspace.data.di.DataModule
import com.uruklabs.newsspace.domain.di.DomainModule
import com.uruklabs.newsspace.presentation.di.PresententionModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        PresententionModule.load()
        DataModule.load()
        DomainModule.load()
    }
}