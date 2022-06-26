package com.karry.chatapp

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import com.google.android.material.color.DynamicColors
import com.karry.common.logger.DeviceDetails
import com.karry.common.logger.TimberRemoteTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ChatApp: Application() {

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            val deviceDetails = DeviceDetails(
                deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID),
                appVersionName = BuildConfig.VERSION_NAME,
                appVersionCode = BuildConfig.VERSION_CODE)
            val remoteTree = TimberRemoteTree(deviceDetails)
            Timber.plant(remoteTree)
        }
    }
}