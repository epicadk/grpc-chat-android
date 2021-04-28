package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grpc_chat_android.databinding.FragmentChatListBinding
import com.example.grpc_chat_android.db.entities.ChatPreview
import com.example.grpc_chat_android.view.adapter.ChatAdapter
import com.example.grpc_chat_android.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatListFragment : Fragment() {
  private var _binding: FragmentChatListBinding? = null
  private val binding
    get() = _binding!!

  private val viewModel: MainActivityViewModel by activityViewModels()
  private lateinit var chatList: List<ChatPreview>
  private val chatAdapter: ChatAdapter by lazy { ChatAdapter() }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentChatListBinding.inflate(inflater, container, false)
    binding.fbAddChat.setOnClickListener {
      this.findNavController()
        .navigate(ChatListFragmentDirections.actionChatListFragmentToAddUserFragment())
    }
    binding.rvChatList.layoutManager = LinearLayoutManager(context)
    viewModel.allChatLiveData.observe(viewLifecycleOwner, { chatAdapter.setData(it) })
    binding.rvChatList.adapter = chatAdapter
    return binding.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
