package com.example.grpc_chat_android.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grpc_chat_android.databinding.ActivityMainBinding
import com.example.grpc_chat_android.models.ChatServiceGrpc
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.ManagedChannelBuilder

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    companion object {
        // Probably not the best way to do this
        lateinit var user : String
    }
}