package com.karry.account_feature.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import com.karry.account_feature.R
import com.karry.account_feature.databinding.FragmentRegisterBinding
import com.karry.account_feature.presentation.AccountNavigation
import com.karry.base.BaseFragment
import com.karry.common.Utils.isEmail
import com.karry.common.Utils.passwordValidator
import com.karry.common.Utils.showToast
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [BaseFragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRegisterBinding
        get() = FragmentRegisterBinding::inflate

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun prepareView(savedInstanceState: Bundle?) {
        with(binding) {
            btnToLogin.setOnClickListener {
                AccountNavigation.RegisterToLogin(it.findFragment())
            }
            btnRegister.setOnClickListener {
                onRegisterClick()
            }
        }
    }

    private fun onRegisterClick() {
        with(binding) {
            val email = edtEmailRegister.text.toString()
            val password = edtPasswordRegister.text.toString()
            val confirm = edtConfirmRegister.text.toString()
            val firstName = edtFirstNameRegister.text.toString()
            val lastName = edtLastNameRegister.text.toString()

            if (email.isEmpty() &&
                firstName.isEmpty() &&
                lastName.isEmpty() &&
                password.isEmpty() &&
                confirm.isEmpty()
            ) {
                showToast(R.string.fields_empty)
                return
            }

            if (email.isEmpty() || !email.isEmail()) {
                inpLayoutEmailRegister.error = getString(R.string.email_empty)
                inpLayoutLastNameRegister.requestFocus()
                return
            }


            if (firstName.isEmpty()) {
                inpLayoutFirstNameRegister.error = getString(R.string.first_name_empty)
                inpLayoutFirstNameRegister.requestFocus()
                return
            }

            if (lastName.isEmpty()) {
                inpLayoutLastNameRegister.error = getString(R.string.last_name_empty)
                inpLayoutLastNameRegister.requestFocus()
                return
            }

            if (password.isEmpty()) {
                inpLayoutPasswordRegister.error = getString(R.string.password_empty)
                inpLayoutPasswordRegister.requestFocus()
                return
            }

            if (password.passwordValidator()) {
                inpLayoutPasswordRegister.error = getString(R.string.password_weak)
                inpLayoutPasswordRegister.requestFocus()
                return
            }

            if (confirm.isEmpty()) {
                inpLayoutConfirmRegister.error = getString(R.string.confirm_empty)
                inpLayoutConfirmRegister.requestFocus()
                return
            }

            if (confirm != password) {
                inpLayoutConfirmRegister.error = getString(R.string.confirm_fails)
                inpLayoutConfirmRegister.requestFocus()
                return
            }

            register(email, password, firstName, lastName)
        }
    }

    private fun register(email: String, password: String, firstName: String, lastName: String) {
        registerViewModel.register(email, password, firstName, lastName)
        registerViewModel.state.observe(viewLifecycleOwner) { state ->
            loading(state.isLoading)
            if (state.error != null) {
                showToast(state.error.asString(requireContext()))
            }

            if (state.message != null) {
                showToast(state.message.asString(requireContext()))
                AccountNavigation.RegisterToLogin(this@RegisterFragment)
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        with(binding) {
            loadingRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
            frameRegister.visibility = if (!isLoading) View.VISIBLE else View.GONE
            if (isLoading) {
                inpLayoutEmailRegister.error = ""
                inpLayoutPasswordRegister.error = ""
                inpLayoutConfirmRegister.error = ""
                inpLayoutFirstNameRegister.error = ""
                inpLayoutLastNameRegister.error = ""
            }
        }
    }
}