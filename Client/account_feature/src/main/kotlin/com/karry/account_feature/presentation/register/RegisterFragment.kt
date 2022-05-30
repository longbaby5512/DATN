package com.karry.account_feature.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.karry.account_feature.R
import com.karry.account_feature.databinding.FragmentRegisterBinding
import com.karry.base.BaseFragment


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToLogin.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        // Nothing
    }
}