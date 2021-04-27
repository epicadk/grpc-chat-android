package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.db.dao.ChatDao
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.models.ChatServiceGrpc
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ChatRepository @Inject constructor(
  private val chatDao: ChatDao,
  private val stub: ChatServiceGrpc.ChatServiceStub
) {

    fun login(loginRequest: Chat.LoginRequest, observer: StreamObserver<Chat.Message>) =
        stub.login(loginRequest, observer)

    fun sendMessage(message: Chat.Message, observer: StreamObserver<Chat.Success>) =
        stub.sendChat(message, observer)

    val allChats: Flow<List<ChatEntity>> = chatDao.loadAllChats()

    val chatList = chatDao.loadChatPreview()

    fun loadChat(sender: String) = chatDao.loadOneChat(sender)

    suspend fun insert(message: Chat.Message) {
        chatDao.insertChat(ChatEntity(message))
    }

    suspend fun deleteAll() = chatDao.deleteAll()
}
