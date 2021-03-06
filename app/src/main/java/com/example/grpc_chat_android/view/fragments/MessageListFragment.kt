package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grpc_chat_android.PreferenceManager
import com.example.grpc_chat_android.R
import com.example.grpc_chat_android.databinding.FragmentMessageListBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.Mapper
import com.example.grpc_chat_android.view.activities.MainActivity
import com.example.grpc_chat_android.view.adapter.MessageAdapter
import com.example.grpc_chat_android.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MessageListFragment : Fragment() {
    private var _binding: FragmentMessageListBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: ChatViewModel by activityViewModels()
    private val args by navArgs<MessageListFragmentArgs>()
    private val adapter: MessageAdapter by lazy { MessageAdapter(args.otherUserPhone) }

    @Inject
    lateinit var preferenceManager: PreferenceManager
    private var userPhone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            userPhone = preferenceManager.getUserPhone()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).title =
            args.otherUserPhone
        binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessage.adapter = adapter
        viewModel.loadChat(args.otherUserPhone)
            .observe(viewLifecycleOwner, { adapter.setData(it.map { Mapper.toProto(it) }) })
        binding.messageEt.doOnTextChanged { text, _, _, _ ->
            binding.btMessage.isEnabled = text.toString().isNotBlank()
        }
        binding.btMessage.setOnClickListener {
            if (!userPhone.isNullOrBlank()) {
                viewModel.sendMessage(
                    Chat.Message.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setTo(args.otherUserPhone)
                        .setFrom(userPhone)
                        .setBody(binding.messageEt.text.toString())
                        .build(),
                    args.otherUserPhone,
                    Instant.now().toEpochMilli()
                )
                binding.messageEt.clearFocus()
                binding.messageEt.setText("")
            } else {
                throw Exception("User phone number is null")
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
