package com.karry.chatapp.ui.navigations

import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.karry.chatapp.R
import com.karry.chatapp.domain.model.User
import com.karry.chatapp.ui.account.login.LoginFragmentDirections
import com.karry.chatapp.ui.chat.home.HomeFragmentDirections
import com.karry.chatapp.ui.chat.user_list.UserListFragmentDirections
import com.karry.chatapp.ui.splash.SplashFragmentDirections
import com.karry.chatapp.utils.extentions.onBackPressed

fun Fragment.loginToSignUp() {
    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
}

fun Fragment.loginToHome() {
    // Go to home and clear back stack
    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
}

fun Fragment.splashToLogin() {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(R.id.splashFragment, true)
        .build()
    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment(), navOptions)
}

fun Fragment.splashToHome() {
    val navOptions = NavOptions.Builder()
        .setPopUpTo(R.id.splashFragment, true)
        .build()
    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment(), navOptions)
}

fun Fragment.signUpToLogin() {
    onBackPressed()
}

fun Fragment.homeToUserList() {
    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUserListFragment())
}

fun Fragment.homeToChat(receiverUser: User) {
    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatFragment(receiverUser))
}

fun Fragment.userListToChat(receiverUser: User) {
    findNavController().navigate(UserListFragmentDirections.actionUserListFragmentToChatFragment(receiverUser))
}