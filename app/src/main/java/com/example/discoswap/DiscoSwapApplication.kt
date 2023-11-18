package com.example.discoswap

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DiscoSwapApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
