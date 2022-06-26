package com.karry.data.dto.account


import androidx.annotation.Keep

@Keep
data class ResponseDTO(
    val data: DataDTO?,
    val message: String?,
    val statusCode: Int
)