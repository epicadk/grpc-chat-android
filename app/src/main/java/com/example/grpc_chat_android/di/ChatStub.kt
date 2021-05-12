package com.example.grpc_chat_android.di

import android.content.Context
import com.example.grpc_chat_android.models.ChatServiceGrpc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.grpc.ManagedChannel
import io.grpc.android.AndroidChannelBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatStub {
    @Provides
    @Singleton
    fun provideChannel(@ApplicationContext context: Context): ManagedChannel =
        AndroidChannelBuilder.forAddress("192.168.1.6", 8080).context(context).usePlaintext()
            .build()

    @Provides
    @Singleton
    fun provideStub(channel: ManagedChannel): ChatServiceGrpc.ChatServiceStub =
        ChatServiceGrpc.newStub(channel)
}
