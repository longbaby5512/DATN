package com.karry.chatapp.ui.account.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karry.chatapp.data.dto.request.LoginRequest
import com.karry.chatapp.data.dto.response.toKey
import com.karry.chatapp.data.dto.response.toUser
import com.karry.chatapp.ui.account.login.usecase.LoginUseCase
import com.karry.chatapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state get() = _state.asStateFlow()

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        loginUseCase(loginRequest).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        user = result.data.toUser(),
                        key = result.data.key.toKey(),
                        token = result.data.token
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false,
                        error = result.exception
                    )
                }
                else -> {
                    _state.value = state.value.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}