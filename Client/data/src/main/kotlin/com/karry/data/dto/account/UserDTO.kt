package com.karry.data.dto.account


import androidx.annotation.Keep

@Keep
data class UserDTO(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val status: Boolean?
)