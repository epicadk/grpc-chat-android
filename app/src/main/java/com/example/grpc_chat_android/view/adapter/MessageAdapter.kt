package com.example.grpc_chat_android.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grpc_chat_android.databinding.ItemMessageBinding
import com.example.grpc_chat_android.models.Chat
import java.util.Date

class MessageAdapter(private val messageList : List<Chat.Message>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    class ViewHolder(val binding : ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
         ViewHolder(ItemMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binding.messageBody.text = messageList[position].body
       holder.binding.messageTime.text = Date().time.toString()
    }

    override fun getItemCount() =
        messageList.size

}