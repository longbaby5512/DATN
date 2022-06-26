package com.karry.account_feature.presentation.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import com.karry.account_feature.R
import com.karry.account_feature.databinding.FragmentForgotBinding
import com.karry.account_feature.presentation.AccountNavigation
import com.karry.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotFragment : BaseFragment<FragmentForgotBinding>(R.layout.fragment_forgot) {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentForgotBinding
        get() = FragmentForgotBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToLogin.setOnClickListener {
            AccountNavigation.ForgotToLogin(it.findFragment())
        }
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        // Nothing
    }
}