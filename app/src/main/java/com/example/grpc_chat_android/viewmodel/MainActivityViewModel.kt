package com.example.grpc_chat_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.grpc_chat_android.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: ChatRepository) :
    ViewModel() {

//    private val _message = MutableLiveData<String>()
//    val message: LiveData<String>
//        get() = _message
}
