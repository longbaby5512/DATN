package com.karry.chat_feature

import com.karry.data.models.User

sealed class AuthorizationState {
    object Loading: AuthorizationState()
    class Unauthorized(val message: String): AuthorizationState()
    class Authorized(val user: User.User): AuthorizationState()
}