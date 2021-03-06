package com.example.grpc_chat_android.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.grpc_chat_android.databinding.ItemChatBinding
import com.example.grpc_chat_android.db.entities.ChatPreview
import com.example.grpc_chat_android.view.fragments.ChatListFragmentDirections

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private lateinit var chatList: List<ChatPreview>

    fun setData(data: List<ChatPreview>) {
        chatList = data
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.chatTitle.text = chatList[position].chatId
        // holder.binding.chatLast.text = chatList[position].sender
        // holder.binding.messageTime.text = Date().time.toString()
        holder.binding.root.setOnClickListener {
            it.findNavController()
                .navigate(
                    ChatListFragmentDirections.actionChatListFragmentToMessageListFragment(
                        chatList[position].chatId
                    )
                )
        }
    }

    override fun getItemCount() = if (::chatList.isInitialized) chatList.size else 0
}
