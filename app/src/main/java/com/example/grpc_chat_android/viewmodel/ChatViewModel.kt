package com.example.grpc_chat_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun connect(phone: Chat.Phone) {
        repository.connect(phone, object : StreamObserver<Chat.Message> {
            override fun onNext(value: Chat.Message?) {
                if (value != null) viewModelScope.launch {
                    repository.insert(value, value.from, value.time)
                }
            }

            override fun onError(t: Throwable?) {
                _message.postValue(t?.message)
            }

            override fun onCompleted() {
            }
        })
    }

    val allChatLiveData = repository.chatList.asLiveData()

    fun sendMessage(message: Chat.Message, otherUser: String, time: Long) {
        repository.sendMessage(message, object : StreamObserver<Chat.Success> {
            override fun onNext(value: Chat.Success?) {}

            override fun onError(t: Throwable?) {
                _message.postValue(t?.message)
            }

            override fun onCompleted() {
                // DO nothing
            }
        })
        // add message to local storage
        viewModelScope.launch { repository.insert(message, otherUser, time) }
    }

    fun loadChat(chatId: String) = repository.loadChat(chatId).asLiveData()
}
