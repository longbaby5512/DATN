package com.karry.account_feature.presentation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.karry.account_feature.AccountActivity
import com.karry.account_feature.R
import com.karry.common.Utils.onBackPressed

sealed class AccountNavigation(@IdRes protected val resId: Int? = null) {
    class LoginToRegister(fragment: Fragment): AccountNavigation(R.id.action_LoginFragment_to_RegisterFragment) {
        init {
            fragment.findNavController().navigate(resId!!)
        }
    }
    class LoginToForgot(fragment: Fragment): AccountNavigation(R.id.action_LoginFragment_to_ForgotFragment) {
        init {
            fragment.findNavController().navigate(resId!!)
        }
    }
    class RegisterToLogin(fragment: Fragment): AccountNavigation() {
        init {
            fragment.onBackPressed()
        }
    }
    class ForgotToLogin(fragment: Fragment): AccountNavigation() {
        init {
            fragment.onBackPressed()
        }
    }
}
