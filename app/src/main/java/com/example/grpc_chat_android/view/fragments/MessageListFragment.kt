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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageListFragment : Fragment() {
  private var _binding: FragmentMessageListBinding? = null
  private val binding
    get() = _binding!!
  private val viewModel: MainActivityViewModel by activityViewModels()
  private val argument: MessageListFragmentArgs by navArgs()
  private val adapter: MessageAdapter by lazy { MessageAdapter() }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMessageListBinding.inflate(inflater, container, false)
    binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
    binding.rvMessage.adapter = adapter
    adapter.setSenderId(argument.chatid)
    viewModel
      .loadChat(argument.chatid)
      .observe(viewLifecycleOwner, { adapter.setData(it.map { Mapper.toProto(it) }) })
    binding.btMessage.setOnClickListener {
      viewModel.sendMessage(
        Chat.Message.newBuilder()
          .setReceiver(argument.chatid)
          // TODO should not be hardcoded
          .setSender("cool")
          .setBody(binding.messageEt.text.toString())
          .build()
      )
      binding.messageEt.clearFocus()
      binding.messageEt.setText("")
    }
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
