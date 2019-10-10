package com.example.water13.component

import android.app.Application
import com.example.water13.source.Repo
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Repo.init(this)
        Timber.plant(Timber.DebugTree())
    }
}