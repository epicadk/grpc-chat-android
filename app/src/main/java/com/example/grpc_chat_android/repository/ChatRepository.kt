package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.db.dao.ChatDao
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.models.ChatServiceGrpc
import io.grpc.stub.StreamObserver
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatDao: ChatDao,
    private val stub: ChatServiceGrpc.ChatServiceStub,
    private val interceptor: NetworkInterceptor
) {
    fun signUp(user: Chat.User, observer: StreamObserver<Chat.Success>) =
        stub.register(user, observer)

    fun login(loginRequest: Chat.LoginRequest, observer: StreamObserver<Chat.LoginResponse>) =
        stub.login(loginRequest, observer)

    fun connect(observer: StreamObserver<Chat.Message>): StreamObserver<Chat.Message> =
        stub.withInterceptors(interceptor).connect(observer)

    val chatList = chatDao.loadChatPreview()

    fun loadChat(chatId: String) = chatDao.loadOneChat(chatId)

    suspend fun insert(message: Chat.Message, chatId: String, time: Long) {
        chatDao.insertChat(ChatEntity(message, chatId, time))
    }

    suspend fun deleteAll() = chatDao.deleteAll()
}
