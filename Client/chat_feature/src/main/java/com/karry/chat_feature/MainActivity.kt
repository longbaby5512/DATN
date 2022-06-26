package com.karry.chat_feature

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.karry.base.BaseActivity
import com.karry.chat_feature.databinding.ActivityMainBinding
import com.karry.chat_feature.presentation.logout.LogoutViewModel
import com.karry.common.Constants
import com.karry.common.Utils.showToast
import com.karry.common.storage.Storage
import com.karry.data.models.User
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    override val bindLayout: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    @Inject
    lateinit var storage: Storage

    private val logoutViewModel: LogoutViewModel by viewModels()

    private lateinit var navController: NavController

    override fun prepareView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)
        val fullName = storage.get(Constants.KEY_CURRENT_USER, User.User::class.java).fullName
        Timber.d(fullName)
        binding.toolbar.title = fullName

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_chat) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        logoutViewModel.logout(storage.get(Constants.KEY_TOKEN, String::class.java))
        logoutViewModel.state.observe(this) { state ->
            loading(state.isLoading)
            if (state.error != null) {
                showToast(state.error.asString(this))
            }
            if (state.message != null) {
                showToast(state.message.asString(this))
                storage.clear()
                Intent(this, Class.forName(Constants.ACCOUNT_ACTIVITY)).let {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        Timber.d("Loading $isLoading")
        if (isLoading) {
            binding.activityLayout.visibility = View.GONE
            supportActionBar?.hide()
            binding.logoutFrame.visibility = View.VISIBLE
        } else {
            binding.activityLayout.visibility = View.VISIBLE
            supportActionBar?.show()
            binding.logoutFrame.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}