package com.example.grpc_chat_android.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grpc_chat_android.databinding.ItemMessageReceivedBinding
import com.example.grpc_chat_android.databinding.ItemMessageSentBinding
import com.example.grpc_chat_android.models.Chat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MessageAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2
    private lateinit var messageList: List<Chat.Message>
    private lateinit var senderId: String

    fun setSenderId(chatId: String) {
        senderId = chatId
    }

    fun setData(data: List<Chat.Message>) {
        messageList = data
        notifyDataSetChanged()
    }

    class SentMessageHolder(val binding: ItemMessageSentBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ReceivedMessageHolder(val binding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (messageList[position].sender) {
            senderId -> VIEW_TYPE_MESSAGE_RECEIVED
            else -> VIEW_TYPE_MESSAGE_SENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            ReceivedMessageHolder(
                ItemMessageReceivedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            SentMessageHolder(
                ItemMessageSentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_RECEIVED -> {
                (holder as ReceivedMessageHolder).apply {
                    binding.bodyReceivedMessage.text = messageList[position].body
                    binding.timeReceivedMessage.text =
                        LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                }
            }
            VIEW_TYPE_MESSAGE_SENT -> {
                (holder as SentMessageHolder).apply {
                    binding.bodySentMessage.text = messageList[position].body
                    binding.timeSentMessage.text =
                        LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                }
            }
        }
    }

    override fun getItemCount() = if (::messageList.isInitialized) messageList.size else 0
}
