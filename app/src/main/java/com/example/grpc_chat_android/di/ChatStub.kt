package com.example.grpc_chat_android.di

import com.example.grpc_chat_android.models.ChatServiceGrpc
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatStub {
  @Provides
  @Singleton
  fun provideChannel(): Channel =
    ManagedChannelBuilder.forAddress("host", 8080).usePlaintext().build()

  @Provides
  @Singleton
  fun provideStub(channel: Channel): ChatServiceGrpc.ChatServiceStub =
    ChatServiceGrpc.newStub(channel)
}
