package com.karry.account_feature.presentation.login

import com.karry.common.UiText
import com.karry.data.models.User

data class LoginState(
    val isLoading: Boolean = false,
    val user: User.User? = null,
    val token: String = "",
    val error: UiText? = null
)
