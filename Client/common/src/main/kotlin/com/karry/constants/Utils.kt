package com.karry.constants

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

object Utils {
    fun ByteArray.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }
    fun Bitmap.toByteArray(): ByteArray {
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.PNG, 100, this)
            return toByteArray()
        }
    }

    fun Bitmap.toByteArray(quality: Int): ByteArray {
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.PNG, quality, this)
            return toByteArray()
        }
    }

    fun ByteArray.toBitmap(): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(this, 0, size)
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
            return bitmap
        }
    }

    fun ByteArray.toBitmap(quality: Int): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(this, 0, size)
        ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, this)
            return bitmap
        }
    }
}