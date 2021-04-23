package com.example.grpc_chat_android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grpc_chat_android.repository.ChatStub
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val repository: ChatRepository) : ViewModel() {

    fun login(loginRequest: Chat.LoginRequest) {
        ChatStub.Stub.login(loginRequest, object : StreamObserver<Chat.Message> {
            override fun onNext(value: Chat.Message?) {
                if (value != null)
                    viewModelScope.launch {
                        repository.insert(value)
                    }
            }

            override fun onError(t: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onCompleted() {
                TODO("Not yet implemented")
            }

        })
    }

    fun sendMessage(message: Chat.Message) {

        viewModelScope.launch { repository.insert(message) }
        ChatStub.Stub.sendChat(message, object : StreamObserver<Chat.Success> {
            override fun onNext(value: Chat.Success?) {
            TODO("Set message sent")
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