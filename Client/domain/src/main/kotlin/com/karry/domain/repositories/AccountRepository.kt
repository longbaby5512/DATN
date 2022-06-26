package com.karry.domain.repositories

import com.karry.data.dto.account.ResponseDTO
import com.karry.data.models.User.Login
import com.karry.data.models.User.Register

interface AccountRepository {
    suspend fun register(info: Register): ResponseDTO
    suspend fun login(info: Login): ResponseDTO
    suspend fun logout(token: String): ResponseDTO
    suspend fun whoami(token: String): ResponseDTO
}