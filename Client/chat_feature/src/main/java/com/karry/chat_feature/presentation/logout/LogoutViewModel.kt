package com.karry.chat_feature.presentation.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karry.common.Resource
import com.karry.common.UiText
import com.karry.domain.usecase.account.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
): ViewModel() {
    private val _state = MutableLiveData(LogoutState())
    val state: LiveData<LogoutState>
        get() = _state


    fun logout(token: String) {
        logoutUseCase(token).onEach { result ->
            when(result) {
                is Resource.Success -> _state.value = state.value?.copy(message = UiText.DynamicString(result.data), error = null)
                is Resource.Error -> _state.value = state.value?.copy(isLoading = false, error = UiText.DynamicString(result.exception))
                else -> _state.value = state.value?.copy(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }
}


