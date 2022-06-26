package com.karry.domain.usecase.account

import com.karry.common.Resource
import com.karry.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repo: AccountRepository) {
    operator fun invoke(token: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading)
            val message = repo.logout("Bearer $token").message!!
            Timber.d(message)
            emit(Resource.Success(message))
        } catch (e: HttpException) {
            Timber.e(e)
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            Timber.e(e)
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}