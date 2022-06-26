package com.karry.account_feature.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karry.account_feature.R
import com.karry.common.Resource
import com.karry.common.UiText
import com.karry.data.mappers.toUser
import com.karry.data.models.User
import com.karry.domain.usecase.account.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _state = MutableLiveData(LoginState())
    val state: LiveData<LoginState>
        get() = _state

    fun login(email: String, password: String) {
        val loginData = User.Login(email, password)
        loginUseCase(loginData).onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = state.value?.copy(
                    user = result.data.user.toUser(),
                    token = result.data.accessToken,
                    error = null
                )
                is Resource.Error -> _state.value = state.value?.copy(
                    isLoading = false,
                    error = UiText.StringResource(R.string.incorrect)
                )
                else -> _state.value = state.value?.copy(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}

