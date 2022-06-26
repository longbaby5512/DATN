package com.karry.common

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.karry.common.Utils.isEmail
import java.io.ByteArrayOutputStream
import java.util.regex.Pattern

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

    fun Any?.isNull() = this == null
    fun Any?.isNotNull() = this != null

    fun Context.showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.showToast(message: String) {
        requireContext().showToast(message)
    }

    fun Fragment.showToast(@StringRes resId: Int) {
        requireContext().showToast(resId)
    }

    fun Fragment.onBackPressed() {
        requireActivity().onBackPressed()
    }

    fun Fragment.finish() {
        requireActivity().finish()
    }

    private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    fun String.passwordValidator(): Boolean {
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }


    fun String.isEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun Context.showToast(@StringRes resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.dismissKeyboard() {
        requireActivity().dismissKeyboard()
    }

    fun Activity.dismissKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}