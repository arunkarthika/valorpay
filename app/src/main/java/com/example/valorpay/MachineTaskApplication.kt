package com.example.valorpay

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MachineTaskApplication :Application() {
    override fun onCreate() {
        super.onCreate()
    }
}