package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.grpc_chat_android.databinding.FragmentMessageListBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.viewmodel.MainActivityViewModel

class MessageListFragment : Fragment() {
    private var _binding: FragmentMessageListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        binding.btMessage
            .setOnClickListener {
                viewModel.sendMessage(
                    Chat.Message.newBuilder().setReciever("Ross").setSender("cool")
                        .setBody(binding.messageEt.text.toString()).build()
                )
            }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}