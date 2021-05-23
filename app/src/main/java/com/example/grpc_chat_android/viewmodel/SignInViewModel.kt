package com.example.grpc_chat_android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grpc_chat_android.PreferenceManager
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: ChatRepository, private val preferenceManager: PreferenceManager) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun login(loginRequest: Chat.LoginRequest) {
        repository.login(loginRequest, object : StreamObserver<Chat.LoginResponse> {
            override fun onNext(value: Chat.LoginResponse?) {
                _message.postValue("Login Successful")
                // Using runblocking because we need these value ASAP
                runBlocking {
                    preferenceManager.saveToken(value!!.accessToken)
                    preferenceManager.saveUserPhone(loginRequest.phonenumber)
                }
            }

            override fun onError(t: Throwable?) {
                _message.postValue("Login Failed")
            }

            override fun onCompleted() {
            }
        })
    }

    fun signUp(username: String, password: String) {
        val user = Chat.User.newBuilder().setPhonenumber(username).setPassword(password).build()
        repository.signUp(user, object : StreamObserver<Chat.Success> {
            override fun onNext(value: Chat.Success?) {
                _message.postValue("User Registered")
            }

            override fun onError(t: Throwable?) {
                _message.postValue("Registration failed")
            }

            override fun onCompleted() {
            }
        })
    }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }
}
