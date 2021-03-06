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

    private lateinit var requestObserver: StreamObserver<Chat.Message>

    fun connect() {
        requestObserver = repository.connect(object : StreamObserver<Chat.Message> {
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
        try {
            requestObserver.onNext(message)
        } catch (e: RuntimeException) {
            requestObserver.onError(e)
            _message.postValue(e.message)
            throw e
        }

        // add message to local storage
        viewModelScope.launch { repository.insert(message, otherUser, time) }
    }

    fun loadChat(chatId: String) = repository.loadChat(chatId).asLiveData()

    override fun onCleared() {
        requestObserver.onCompleted()
    }
}
