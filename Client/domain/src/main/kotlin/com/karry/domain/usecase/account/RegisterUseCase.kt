package com.karry.domain.usecase.account

import com.karry.common.Resource
import com.karry.data.models.User.Register
import com.karry.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repo: AccountRepository) {
    operator fun invoke(registrationData: Register): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading)
            val message = repo.register(registrationData).message!!
            emit(Resource.Success(message))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}