package com.example.grpc_chat_android.repository

import com.example.grpc_chat_android.db.entities.ChatEntity
import com.example.grpc_chat_android.models.Chat

object Mapper {
    fun toProto(entity: ChatEntity): Chat.Message {
        return Chat.Message.newBuilder()
            .apply {
                id = entity.id
                body = entity.body
                from = entity.from
                time = entity.time
            }
            .build()
    }

    fun fromProto(message: Chat.Message): ChatEntity {
        return ChatEntity(message.id, message.body, message.from, message.to, null, message.time)
    }
}
