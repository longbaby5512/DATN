package com.karry.chatapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.karry.base.BaseActivity
import com.karry.chatapp.R
import com.karry.chatapp.databinding.ActivitySplashBinding
import com.karry.common.Constants
import com.karry.common.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    @Inject
    lateinit var storage: Storage

    override val bindLayout: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background)
        binding.appName.animate().alpha(1f).duration = 2000L
        val handler = Handler(Looper.getMainLooper())

        Timber.d(Constants.KEY_TOKEN)

        val intent: Intent = if (storage.get(Constants.KEY_TOKEN, String::class.java).isEmpty()) {
            Intent(this, Class.forName(Constants.ACCOUNT_ACTIVITY))
        } else {
            Intent(this, Class.forName(Constants.MAIN_ACTIVITY))
        }

        handler.postDelayed({
            startActivity(intent)
            finish()
        }, 2500)
    }
}