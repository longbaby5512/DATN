package com.karry.chatapp.ui.chat.chat.usecase

import android.text.TextUtils
import com.karry.chaotic.ChaoticCypher
import com.karry.chatapp.ChatApplication
import com.karry.chatapp.data.dto.response.toMessage
import com.karry.chatapp.domain.model.Message
import com.karry.chatapp.domain.repositories.AuthRepository
import com.karry.chatapp.utils.KEY_FINAL
import com.karry.chatapp.utils.Resource
import com.karry.chatapp.utils.extentions.fromBase64
import com.karry.chatapp.utils.extentions.fromHexString
import com.karry.chatapp.utils.storage.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class GetAllMessagesUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val chaoticCypher: ChaoticCypher,
    private val storage: Storage
) {
    operator fun invoke(token: String, id: Int, publicKey: String): Flow<Resource<List<Message>>> =
        flow {
            emit(Resource.Loading)
            try {
                val bearerToken = "Bearer $token"

                val suffix = listOf(id, ChatApplication.currentUser!!.id)
                val suffixSorted= suffix.sorted().joinToString("_")
                val keyStore = KEY_FINAL+"_"+suffixSorted
                var key = storage.get(keyStore, String::class.java)
                if (TextUtils.isEmpty(key)) {
                    key = authRepository.getFinalKey(bearerToken, publicKey)
                }

                if (TextUtils.isEmpty(key)) {
                    emit(Resource.Error("Key is empty"))
                    return@flow
                }

                Timber.e("Final key: $key at keyStore: $keyStore")
                storage.set(keyStore, key)
                val response = authRepository.getAllMessages(bearerToken, id)
                if (response.isNotEmpty()) {
                    emit(Resource.Success(response.map {
                        val content = it.content
                        chaoticCypher.init(ChaoticCypher.DECRYPT_MODE, key.toByteArray())
                        val decryptedContent = chaoticCypher.doFinal(content.fromBase64())
                        Timber.e("cypherContent $content decryptedContent: $decryptedContent")
                        return@map it.copy(content = String(decryptedContent))
                            .toMessage()
                    }))
                } else {
                    emit(Resource.Empty)
                }
            } catch (e: HttpException) {
                Timber.e(e)
                emit(Resource.Error(e.message()))
            } catch (e: IOException) {
                Timber.e(e)
                emit(Resource.Error(e.message ?: "Error Occurred!"))
            } catch (e: Exception) {
                Timber.e(e)
                emit(Resource.Error(e.message ?: "Error Occurred!"))
            }
        }
}