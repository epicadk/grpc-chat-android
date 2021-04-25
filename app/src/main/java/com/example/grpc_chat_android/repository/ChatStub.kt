package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.models.ChatServiceGrpc
import io.grpc.ManagedChannelBuilder

object ChatStub {
    private val channel = ManagedChannelBuilder
        .forAddress("host",8080).usePlaintext().build()
    val Stub: ChatServiceGrpc.ChatServiceStub = ChatServiceGrpc.newStub(channel)

}