package com.karry.account_feature.presentation.register

import com.karry.common.UiText

data class RegisterState(
    val isLoading: Boolean = false,
    val message: UiText?= null,
    val error: UiText?= null
)