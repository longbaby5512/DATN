package com.karry.constants

object Constants {

    /**
     * A native method that is implemented by the 'constants' native library,
     * which is packaged with this application.
     */
    external fun getBaseUrl(): String


    // Used to load the 'constants' library on application startup.
    init {
        System.loadLibrary("constants")
    }
}