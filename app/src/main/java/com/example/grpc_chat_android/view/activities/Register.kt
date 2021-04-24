package com.example.grpc_chat_android.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grpc_chat_android.databinding.ActivityRegisterBinding
import com.example.grpc_chat_android.view.activities.MainActivity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent

@AndroidEntryPoint
class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // TODO store this in protoDataStore or shared pref
        MainActivity.user = binding.registerEt.text.toString()
    }
}