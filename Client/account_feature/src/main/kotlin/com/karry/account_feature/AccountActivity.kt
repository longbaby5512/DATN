package com.karry.account_feature

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.karry.account_feature.databinding.ActivityAccountBinding
import com.karry.base.BaseActivity


class AccountActivity() : BaseActivity<ActivityAccountBinding>() {
    override val bindLayout: (LayoutInflater) -> ActivityAccountBinding
        get() = ActivityAccountBinding::inflate

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }
}