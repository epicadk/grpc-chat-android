package com.example.grpc_chat_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.grpc_chat_android.models.ChatServiceGrpc
import io.grpc.ManagedChannelBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}