package com.example.grpc_chat_android.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.db.entities.ChatPreview
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertChat(chat: ChatEntity)

    @Query("DELETE FROM ChatEntity")
    suspend fun deleteAll()

    @Query("SELECT DISTINCT(sender) FROM ChatEntity")
    fun loadChatPreview(): Flow<List<ChatPreview>>

    @Query("SELECT * FROM ChatEntity WHERE sender = :sender OR receiver =:sender")
    fun loadOneChat(sender: String): Flow<List<ChatEntity>>
}
