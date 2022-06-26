package com.karry.chat_feature.presentation.logout

import com.karry.common.UiText

data class LogoutState(
    val isLoading: Boolean = false,
    val message: UiText? = null,
    val error: UiText? = null
)