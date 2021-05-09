package com.example.grpc_chat_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.stub.StreamObserver
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun SignUp(username: String, password: String) {
        val user = Chat.User.newBuilder().setPhonenumber(username).setPassword(password).build()
        repository.signUp(user, object : StreamObserver<Chat.Success> {
            override fun onNext(value: Chat.Success?) {
                _message.postValue("Great Success")
            }

            override fun onError(t: Throwable?) {
                _message.postValue("something went wrong")
            }

            override fun onCompleted() {
            }
        })
    }
}
