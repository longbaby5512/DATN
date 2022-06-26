package com.karry.common

class Constants {

    private external fun getBaseUrl(): String
    private external fun getSharedPrefName(): String
    private external fun getKeyToken(): String
    private external fun getKeyUser(): String

    companion object {
        init {
            System.loadLibrary("constants")
        }

        val AUTH_BASE_URL = "${Constants().getBaseUrl()}auth/"
        val SHARED_PREF_NAME = Constants().getSharedPrefName()
        val KEY_TOKEN = Constants().getKeyToken()
        val KEY_CURRENT_USER = Constants().getKeyUser()
        const val MAIN_ACTIVITY = "com.karry.chat_feature.MainActivity"
        const val ACCOUNT_ACTIVITY = "com.karry.account_feature.AccountActivity"
    }


}