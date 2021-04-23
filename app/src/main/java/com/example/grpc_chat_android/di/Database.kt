package com.example.grpc_chat_android.di

import android.content.Context
import com.example.grpc_chat_android.db.MessageDatabase
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Database {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        MessageDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideRepository(messageDatabase: MessageDatabase) =
        ChatRepository(messageDatabase.chatDao())
}