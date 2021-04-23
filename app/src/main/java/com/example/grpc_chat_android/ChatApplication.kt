package com.example.grpc_chat_android

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.grpc_chat_android.db.MessageDatabase
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChatApplication : Application()