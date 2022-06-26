package com.karry.remote.repositories

import com.karry.data.dto.account.ResponseDTO
import com.karry.data.models.User
import com.karry.remote.api.AuthApi
import com.karry.domain.repositories.AccountRepository

class AccountRepositoryImpl constructor(private val api: AuthApi): AccountRepository {
    override suspend fun register(info: User.Register): ResponseDTO {
        return api.register(info)
    }

    override suspend fun login(info: User.Login): ResponseDTO {
        return api.login(info)
    }

    override suspend fun logout(token: String): ResponseDTO {
        return api.logout(token)
    }


    override suspend fun whoami(token: String): ResponseDTO {
        return api.whoami(token)
    }


}