package com.karry.data.dto.account


import androidx.annotation.Keep

@Keep
data class DataDTO(
    val accessToken: String,
    val user: UserDTO
)