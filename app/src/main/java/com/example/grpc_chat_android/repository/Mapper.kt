package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat

object Mapper {
    fun toProto(entity: ChatEntity): Chat.Message {
        return Chat.Message.newBuilder()
            .apply {
                body = entity.body
                from = entity.sender
                time = entity.time
            }
            .build()
    }

    fun fromProto(message: Chat.Message): ChatEntity {
        return ChatEntity(0, message.body, message.from, message.to, 0, message.time)
    }
}
