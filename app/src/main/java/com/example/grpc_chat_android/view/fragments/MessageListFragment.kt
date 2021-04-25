package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grpc_chat_android.databinding.FragmentMessageListBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.Mapper
import com.example.grpc_chat_android.view.adapter.MessageAdapter
import com.example.grpc_chat_android.viewmodel.MainActivityViewModel

class MessageListFragment : Fragment() {
    private var _binding: FragmentMessageListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityViewModel by activityViewModels()
    private val argument: MessageListFragmentArgs by navArgs()
    private val adapter: MessageAdapter by lazy {
        MessageAdapter()
    }
    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
        viewModel.messageLiveData.observe(viewLifecycleOwner, {
            adapter.messageList = it.map { Mapper.toProto(it) }
        })
        binding.btMessage
            .setOnClickListener {
                viewModel.sendMessage(
                    Chat.Message.newBuilder().setReciever(argument.chatid)
                        .setSender("cool")
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
