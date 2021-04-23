package com.example.grpc_chat_android.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertChat(chat : ChatEntity)
    @Query("SELECT * FROM ChatEntity")
    fun loadAllChats() : Flow<List<ChatEntity>>
}