package com.karry.domain.usecase.account

import com.karry.common.Resource
import com.karry.data.dto.account.DataDTO
import com.karry.data.models.User
import com.karry.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repo: AccountRepository) {
    operator fun invoke(loginData: User.Login): Flow<Resource<DataDTO>> = flow {
        try {
            emit(Resource.Loading)
            val data = repo.login(loginData).data!!
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}