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
class LoginViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun register(user: Chat.User) {
        repository.signUp(user, object : StreamObserver<Chat.Success> {
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

    suspend fun saveUser(userId: String, context: Context, key: Preferences.Key<String>) {
        context.dataStore.edit {
            it[key] = userId
        }
    }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }
}
