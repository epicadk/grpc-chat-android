package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.db.dao.ChatDao
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat
import kotlinx.coroutines.flow.Flow

class ChatRepository(private val chatDao: ChatDao) {
    val allChats: Flow<List<ChatEntity>> = chatDao.loadAllChats()
    val chatList = chatDao.loadChatPreview()

    fun loadChat(sender: String) = chatDao.loadOneChat(sender)

    suspend fun insert(message: Chat.Message) {
        chatDao.insertChat(ChatEntity(message))
    }

    suspend fun deleteAll() = chatDao.deleteAll()
}
