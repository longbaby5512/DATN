package com.karry.common.logger

import android.os.Build

data class DeviceDetails(
    val deviceId: String,
    val osVersion: String = Build.VERSION.RELEASE,
    val manufacturer: String = Build.MANUFACTURER,
    val brand: String = Build.BRAND,
    val device: String = Build.DEVICE,
    val model: String = Build.MODEL,
    val appVersionName: String,
    val appVersionCode: Int
)
