package com.example.grpc_chat_android.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
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

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun signUp(username: String, password: String) {
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

    suspend fun saveUserPhone(phone: String, context: Context, key: Preferences.Key<String>) {
        context.dataStore.edit { user ->
            user[key] = phone
        }
    }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }
}
