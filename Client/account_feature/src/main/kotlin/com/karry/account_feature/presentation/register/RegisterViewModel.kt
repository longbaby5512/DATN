package com.karry.account_feature.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karry.common.Resource
import com.karry.common.UiText
import com.karry.data.models.User
import com.karry.domain.usecase.account.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _state = MutableLiveData(RegisterState())
    val state: LiveData<RegisterState>
        get() = _state

    fun register(email: String, password: String, firstName: String, lastName: String) {
        val registrationData = User.Register(email, password, firstName, lastName)
        registerUseCase(registrationData).onEach { result ->
            when (result) {
                is Resource.Success -> _state.value = state.value?.copy(message = UiText.DynamicString(result.data), error = null)
                is Resource.Error -> _state.value = state.value?.copy(isLoading = false, error = UiText.DynamicString(result.exception))
                else -> _state.value = state.value?.copy(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}