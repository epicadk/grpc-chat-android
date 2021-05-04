package com.example.grpc_chat_android.di

import android.content.Context
import androidx.room.Room
import com.example.grpc_chat_android.db.MessageDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): MessageDatabase {
        return synchronized(this) {
            Room.databaseBuilder(context.applicationContext, MessageDatabase::class.java, "chat_database")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    @Singleton
    @Provides
    fun provideChatDao(messageDatabase: MessageDatabase) = messageDatabase.chatDao()
}
