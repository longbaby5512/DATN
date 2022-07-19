package com.karry.chatapp.ui.chat.chat.usecase

import android.text.TextUtils
import com.google.gson.Gson
import com.karry.chaotic.ChaoticCypher
import com.karry.chatapp.ChatApplication
import com.karry.chatapp.data.api.ChatService
import com.karry.chatapp.data.dto.request.MessageSent
import com.karry.chatapp.data.dto.response.StatusResponse
import com.karry.chatapp.data.dto.response.toMessage
import com.karry.chatapp.domain.model.Message
import com.karry.chatapp.utils.KEY_FINAL
import com.karry.chatapp.utils.Resource
import com.karry.chatapp.utils.extentions.fromBase64
import com.karry.chatapp.utils.extentions.fromHexString
import com.karry.chatapp.utils.extentions.toBase64String
import com.karry.chatapp.utils.extentions.toHex
import com.karry.chatapp.utils.storage.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ChatRealtimeUseCase @Inject constructor(
    private val chatService: ChatService,
    private val chaoticCypher: ChaoticCypher,
    private val storage: Storage
) {
    suspend fun connectToService() {
        chatService.connect()

    }

    fun receiveMessage(): Flow<Resource<Message>> = flow {
        chatService.onMessageReceived().collect {
            val suffix = listOf(it.from, it.to)
            val suffixSorted = suffix.sorted().joinToString("_")
            val keyStore = KEY_FINAL+"_"+suffixSorted
            val key = storage.get(keyStore, String::class.java)
            if (TextUtils.isEmpty(key)) {
                emit(Resource.Error("Key is empty"))
                return@collect
            }
            Timber.d("Final key: $key at $keyStore")
            Timber.d("Message received before decrypt: ${it.content}")
            chaoticCypher.init(ChaoticCypher.DECRYPT_MODE, key.toByteArray())
            val decryptedMessage = chaoticCypher.doFinal(it.content.fromBase64())
            val message = it.copy(content = String(decryptedMessage)).toMessage()
            Timber.d("Message received after decrypt: ${message.content}")
            emit(Resource.Success(message))
        }
    }

    fun sendMessage(message: MessageSent): Flow<Resource<Message>> = flow {
        val suffix = listOf(message.to, ChatApplication.currentUser!!.id)
        val suffixSorted = suffix.sorted().joinToString("_")
        val keyStore = KEY_FINAL+"_"+suffixSorted
        val key = storage.get(keyStore, String::class.java)
        if (TextUtils.isEmpty(key)) {
            emit(Resource.Error("Key is empty"))
            return@flow
        }
        Timber.d("Final key: $key at $keyStore")
        val content = message.content.toByteArray()

        Timber.d("Message sent before encrypt: ${message.content}")

        chaoticCypher.init(ChaoticCypher.ENCRYPT_MODE, key.toByteArray())
        val encryptMessage = chaoticCypher.doFinal(content)
        Timber.d("Message sent after encrypt: ${encryptMessage.toBase64String()}")
        val messageSent = message.copy(content = encryptMessage.toBase64String())
        val result = chatService.sendMessage(messageSent)
        val res = Gson().fromJson(Gson().toJson(result), StatusResponse::class.java)

        if (res.status) {
            emit(Resource.Success(res.message!!.copy(content = message.content).toMessage()))
        } else {
            emit(Resource.Error("Can't send message"))
        }
    }

    suspend fun disconnectFromService() {
        chatService.off()
        chatService.disconnect()
    }
}