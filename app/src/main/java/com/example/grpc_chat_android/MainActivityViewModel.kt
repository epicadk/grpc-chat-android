package com.example.grpc_chat_android

import androidx.lifecycle.ViewModel
import com.example.grpc_chat_android.models.Chat
import io.grpc.stub.StreamObserver

class MainActivityViewModel : ViewModel() {
    fun login(loginRequest: Chat.LoginRequest){
        ChatStub.Stub.login(loginRequest, object : StreamObserver<Chat.Message>{
            override fun onNext(value: Chat.Message?) {
                TODO("Not yet implemented")
            }

            override fun onError(t: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onCompleted() {
                TODO("Not yet implemented")
            }

        })
    }
    fun sendMessage(message: Chat.Message){
        ChatStub.Stub.sendChat(message , object : StreamObserver<Chat.Success>{
            override fun onNext(value: Chat.Success?) {
                TODO("Not yet implemented")
            }

            override fun onError(t: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onCompleted() {
                TODO("Not yet implemented")
            }

        })
    }
}