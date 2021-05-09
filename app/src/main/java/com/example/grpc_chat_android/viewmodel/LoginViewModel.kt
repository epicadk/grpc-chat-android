package com.example.grpc_chat_android.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grpc_chat_android.di.dataStore
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: ChatRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    suspend fun saveUser(userId: String, context: Context, key: Preferences.Key<String>) {
        context.dataStore.edit {
            it[key] = userId
        }
    }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }
}
