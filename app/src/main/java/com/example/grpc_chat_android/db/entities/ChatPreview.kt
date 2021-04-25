package com.example.grpc_chat_android.db.entities

import androidx.room.ColumnInfo

data class ChatPreview (
    @ColumnInfo(name = "sender") val sender : String
        )