package com.example.grpc_chat_android

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grpc_chat_android.databinding.ItemChatBinding
import com.example.grpc_chat_android.databinding.ItemMessageBinding
import com.example.grpc_chat_android.models.Chat
import java.util.Date

class ChatAdapter(private val chatList : List<Chat.Message>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  =
            ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.chatTitle.text = chatList[position].sender
        holder.binding.chatLast.text = chatList[position].sender
        //holder.binding.messageTime.text = Date().time.toString()
    }

    override fun getItemCount() =
            chatList.size

}