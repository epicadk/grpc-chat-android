package com.example.grpc_chat_android

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.grpc_chat_android.db.MessageDatabase
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp(MultiDexApplication::class)
class ChatApplication : Application() {
    private val database by lazy { MessageDatabase.getDatabase(this) }
    val repository by lazy { ChatRepository(database.chatDao()) }
}