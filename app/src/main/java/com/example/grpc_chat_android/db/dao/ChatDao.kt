package com.example.grpc_chat_android.db.dao

import androidx.room.*
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.db.entities.ChatPreview
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertChat(chat: ChatEntity)

    @Delete
    suspend fun deleteAll()

    @Query("SELECT * FROM ChatEntity ")
    fun loadAllChats(): Flow<List<ChatEntity>>

    @Query("SELECT DISTINCT(sender) FROM ChatEntity")
    fun loadChatPreview(): Flow<List<ChatPreview>>

    @Query("SELECT * FROM ChatEntity where sender = :sender")
    fun loadOneChat(sender: String): Flow<List<ChatEntity>>
}
