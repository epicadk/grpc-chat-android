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
class MainActivityViewModel @Inject constructor(private val repository: ChatRepository) :
    ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun login(loginRequest: Chat.LoginRequest) {
        repository.login(
            loginRequest,
            object : StreamObserver<Chat.Message> {
                override fun onNext(value: Chat.Message?) {
                    // TODO add in rv
                    if (value != null) viewModelScope.launch { repository.insert(value) }
                }

                override fun onError(t: Throwable?) {
                    _message.postValue(t?.message)
                }

                override fun onCompleted() {
                    // DO nothing
                }
            }
        )
    }

    val allChatLiveData = repository.chatList.asLiveData()
}
