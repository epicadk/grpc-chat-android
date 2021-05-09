package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grpc_chat_android.databinding.FragmentMessageListBinding
import com.example.grpc_chat_android.di.dataStore
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.Mapper
import com.example.grpc_chat_android.view.adapter.MessageAdapter
import com.example.grpc_chat_android.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MessageListFragment : Fragment() {
    private var _binding: FragmentMessageListBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: ChatViewModel by activityViewModels()
    private val argument: MessageListFragmentArgs by navArgs()
    private val adapter: MessageAdapter by lazy { MessageAdapter() }

    @Inject
    lateinit var key: Preferences.Key<String>
    private var userId: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessage.adapter = adapter
        adapter.setSenderId(argument.senderId)
        lifecycleScope.launchWhenCreated {
            userId = requireContext().dataStore.data.map { it[key] }.first()
        }
        viewModel.loadChat(argument.senderId)
            .observe(viewLifecycleOwner, { adapter.setData(it.map { Mapper.toProto(it) }) })
        binding.btMessage.setOnClickListener {
            viewModel.sendMessage(
                Chat.Message.newBuilder()
                    .setTo(argument.senderId)
                    // TODO should not be hardcoded
                    .setFrom(userId)
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
