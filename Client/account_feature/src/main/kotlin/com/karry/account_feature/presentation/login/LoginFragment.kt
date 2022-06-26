package com.karry.account_feature.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import com.karry.account_feature.R
import com.karry.account_feature.databinding.FragmentLoginBinding
import com.karry.account_feature.presentation.AccountNavigation
import com.karry.base.BaseFragment
import com.karry.common.Constants
import com.karry.common.Utils.dismissKeyboard
import com.karry.common.Utils.finish
import com.karry.common.Utils.showToast
import com.karry.common.storage.Storage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var storage: Storage

    override fun prepareView(savedInstanceState: Bundle?) {
        with(binding) {
            btnLogin.setOnClickListener {
                onLoginClick()
            }
            btnToRegister.setOnClickListener {
                AccountNavigation.LoginToRegister(it.findFragment())
            }
            btnToForgotPassword.setOnClickListener {
                AccountNavigation.LoginToForgot(it.findFragment())
            }
        }
    }

    private fun onLoginClick() {
        with(binding) {
            dismissKeyboard()

            val email = edtEmailLogin.text.toString()
            val password = edtPasswordLogin.text.toString()

            if (email.isEmpty() && password.isEmpty()) {
                showToast(R.string.fields_empty)
                return
            }

            if (email.isEmpty()) {
                inpLayoutEmailLogin.error = getString(R.string.email_empty)
                inpLayoutEmailLogin.requestFocus()
                return
            }

            if (password.isEmpty()) {
                inpLayoutPasswordLogin.error = getString(R.string.password_empty)
                inpLayoutPasswordLogin.requestFocus()
                return
            }

            login(email, password)

        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            edtEmailLogin.setText("")
            edtPasswordLogin.setText("")
            edtEmailLogin.clearComposingText()
            edtPasswordLogin.clearComposingText()
            inpLayoutEmailLogin.error = ""
            inpLayoutPasswordLogin.error = ""
        }
    }

    private fun login(email: String, password: String) {
        loginViewModel.login(email, password)
        loginViewModel.state.observe(viewLifecycleOwner) { state ->
            loading(state.isLoading)
            if (state.error != null) {
                showToast(state.error.asString(requireContext()))
            }

            if (state.user != null && state.token.isNotEmpty()) {
                storage.set(Constants.KEY_TOKEN, state.token)
                storage.set(Constants.KEY_CURRENT_USER, state.user)
                Intent(requireContext(), Class.forName(Constants.MAIN_ACTIVITY)).let {
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        with(binding) {
            frameLogin.visibility = if (isLoading) View.GONE else View.VISIBLE
            loadingLogin.visibility = if (isLoading) View.VISIBLE else View.GONE

            if (isLoading) {
                inpLayoutEmailLogin.error = ""
                inpLayoutPasswordLogin.error = ""
            }

        }
    }
}