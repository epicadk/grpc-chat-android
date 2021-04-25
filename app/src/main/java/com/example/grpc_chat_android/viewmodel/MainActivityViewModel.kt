package com.example.grpc_chat_android.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.ChatRepository
import com.example.grpc_chat_android.repository.ChatStub
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val repository: ChatRepository) : ViewModel() {
    val allChatLiveData = repository.chatList.asLiveData()
    val messageLiveData = repository.allChats.asLiveData()
    private val _message = MutableLiveData<String>()
    val message : LiveData<String>
    get() =_message
    fun login(loginRequest: Chat.LoginRequest) {
        ChatStub.Stub.login(loginRequest, object : StreamObserver<Chat.Message> {
            override fun onNext(value: Chat.Message?) {
                // TODO add in rv
                if (value != null)
                    viewModelScope.launch {
                        repository.insert(value)
                    }
            }

            override fun onError(t: Throwable?) {
                _message.postValue(t?.message)

            }

            override fun onCompleted() {
                // DO nothing
            }

        })
    }

    fun sendMessage(message: Chat.Message) {

        viewModelScope.launch { repository.insert(message) }
        ChatStub.Stub.sendChat(message, object : StreamObserver<Chat.Success> {
            override fun onNext(value: Chat.Success?) {
                // TODO add in RV
                Log.d("niggs", "onNext: ")
            }

            override fun onError(t: Throwable?) {
                _message.postValue(t?.message)
            }

            override fun onCompleted() {
                // DO nothing
            }

        })
    }
}