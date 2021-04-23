package com.example.grpc_chat_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grpc_chat_android.databinding.ActivityMainBinding
import com.example.grpc_chat_android.models.ChatServiceGrpc
import io.grpc.ManagedChannelBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}