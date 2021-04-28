package com.example.grpc_chat_android.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.grpc_chat_android.db.dao.ChatDao
import com.example.grpc_chat_android.db.entities.ChatEntity

@Database(entities = [ChatEntity::class], version = 2, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {
  abstract fun chatDao(): ChatDao
}
