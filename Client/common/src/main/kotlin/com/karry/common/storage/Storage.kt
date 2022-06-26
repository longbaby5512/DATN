package com.karry.common.storage

interface Storage {
    fun <T> set(key: String, value: T)
    fun <T> get(key: String, type: Class<T>, defValue: T? = null): T
    fun remove(key: String)
    fun clear()
}