package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.db.dao.ChatDao
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.models.ChatServiceGrpc
import io.grpc.stub.StreamObserver
import javax.inject.Inject

class ChatRepository
@Inject
constructor(private val chatDao: ChatDao, private val stub: ChatServiceGrpc.ChatServiceStub) {
    fun signUp(user: Chat.User, observer: StreamObserver<Chat.Success>) =
        stub.register(user, observer)

    fun login(loginRequest: Chat.LoginRequest, observer: StreamObserver<Chat.Message>) =
        stub.login(loginRequest, observer)

    fun sendMessage(message: Chat.Message, observer: StreamObserver<Chat.Success>) =
        stub.sendChat(message, observer)

    val chatList = chatDao.loadChatPreview()

    fun loadChat(sender: String) = chatDao.loadOneChat(sender)

    suspend fun insert(message: Chat.Message) {
        chatDao.insertChat(ChatEntity(message))
    }

    suspend fun deleteAll() = chatDao.deleteAll()
}
