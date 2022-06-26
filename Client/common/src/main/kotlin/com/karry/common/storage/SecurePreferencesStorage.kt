package com.karry.common.storage

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.karry.common.Constants
import com.karry.common.Utils.isNull
import com.securepreferences.SecurePreferences
import javax.inject.Inject

class SecurePreferencesStorage @Inject constructor(context: Context) : Storage {

    private val gson = Gson()

    @SuppressLint("ObsoleteSdkInt")
    private val sharedPreferences: SharedPreferences =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .setRequestStrongBoxBacked(true)
                .build()
            EncryptedSharedPreferences.create(
                context,
                Constants.SHARED_PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            SecurePreferences(context, "karry", "${Constants.SHARED_PREF_NAME}.xml")
        }


    override fun <T> set(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> putString(key, gson.toJson(value))
            }
            apply()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: String, type: Class<T>, defValue: T?): T {

        return when (type) {
            Int::class.java -> sharedPreferences.getInt(key, (defValue ?: 0) as Int) as T
            Boolean::class.java -> sharedPreferences.getBoolean(
                key,
                ((defValue ?: false) as Boolean)
            ) as T
            Float::class.java -> sharedPreferences.getFloat(key, (defValue ?: 0f) as Float) as T
            String::class.java -> sharedPreferences.getString(key, (defValue ?: "").toString()) as T
            Long::class.java -> sharedPreferences.getLong(key, (defValue ?: 0) as Long) as T
            else -> gson.fromJson(
                sharedPreferences.getString(
                    key,
                    if (defValue.isNull()) "" else gson.toJson(defValue)
                ), type
            ) as T
        }
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}