package com.jdc.iotcontrolcenter

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IoTControlCenterApplication : Application() {

    companion object {
        lateinit var CONTEXT : Context
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
    }
}