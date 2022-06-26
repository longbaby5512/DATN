package com.karry.domain.usecase.account

import com.karry.common.Resource
import com.karry.data.mappers.toUser
import com.karry.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class WhoamiUseCase @Inject constructor(private val repo: AccountRepository) {
    operator fun invoke(token: String): Flow<Resource<com.karry.data.models.User.User>> = flow {
        try {
            emit(Resource.Loading)
            val user = repo.whoami("Bearer $token").data!!.user.toUser()
            emit(Resource.Success(user))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message()))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
