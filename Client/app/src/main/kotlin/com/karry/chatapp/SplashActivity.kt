package com.karry.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import com.karry.account_feature.AccountActivity
import com.karry.base.BaseActivity
import com.karry.chatapp.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity() : BaseActivity<ActivitySplashBinding>() {
    override val bindLayout: (LayoutInflater) -> ActivitySplashBinding
    get() = ActivitySplashBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.parseColor("#425C59")
        binding.appName.animate().alpha(1f).duration = 2000L

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}