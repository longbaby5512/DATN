package com.karry.account_feature.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.karry.account_feature.R
import com.karry.account_feature.databinding.FragmentLoginBinding
import com.karry.base.BaseFragment
import com.karry.chaotic.ChaoticCypher
import com.karry.chaotic.ChaoticType
import com.karry.constants.Utils.toHex
import timber.log.Timber


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_RegisterFragment)
        }

        binding.buttonToForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.action_LoginFragment_to_ForgotFragment)
        }
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        // Nothing
    }
}