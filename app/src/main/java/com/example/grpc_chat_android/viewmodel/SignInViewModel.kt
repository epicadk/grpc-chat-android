package com.example.grpc_chat_android.viewmodel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grpc_chat_android.di.dataStore
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: ChatRepository,private val dataStore: DataStore<Preferences> ) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun login(loginRequest: Chat.LoginRequest) {
        repository.login(loginRequest, object : StreamObserver<Chat.LoginResponse> {
            override fun onNext(value: Chat.LoginResponse?) {
                _message.postValue("Login Successful")
                runBlocking {
                    saveToken(value!!.accessToken)
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

    suspend fun saveUserPhone(phone: String, key: Preferences.Key<String>) {
        dataStore.edit { user ->
            user[key] = phone
        }
    }

    suspend fun saveToken(token : String){
        dataStore.edit {jwt ->
            jwt[stringPreferencesKey("jwtToken")] = token
        }
    }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }
}
