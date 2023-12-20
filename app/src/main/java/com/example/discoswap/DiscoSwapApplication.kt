package com.example.discoswap

import android.app.Application
import com.example.discoswap.data.AppContainer
import com.example.discoswap.data.DefaultAppContainer

class DiscoSwapApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
