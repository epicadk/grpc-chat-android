package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grpc_chat_android.R
import com.example.grpc_chat_android.databinding.FragmentMessageListBinding
import com.example.grpc_chat_android.di.dataStore
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.repository.Mapper
import com.example.grpc_chat_android.view.activities.MainActivity
import com.example.grpc_chat_android.view.adapter.MessageAdapter
import com.example.grpc_chat_android.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MessageListFragment : Fragment() {
    private var _binding: FragmentMessageListBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: ChatViewModel by viewModels()
    private val argument: MessageListFragmentArgs by navArgs()
    private val adapter: MessageAdapter by lazy { MessageAdapter() }

    @Inject
    lateinit var key: Preferences.Key<String>
    private var userPhone: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            userPhone = requireContext().dataStore.data.map { it[key] }.first()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageListBinding.inflate(inflater, container, false)
        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).title =
            argument.otherUserPhone
        binding.rvMessage.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessage.adapter = adapter
        adapter.setOtherUserPhone(argument.otherUserPhone)
        viewModel.loadChat(argument.otherUserPhone)
            .observe(viewLifecycleOwner, { adapter.setData(it.map { Mapper.toProto(it) }) })
        binding.messageEt.doOnTextChanged { text, _, _, _ ->
            binding.btMessage.isEnabled = text.toString().isNotBlank()
        }
        binding.btMessage.setOnClickListener {
            viewModel.sendMessage(
                Chat.Message.newBuilder()
                    .setTo(argument.otherUserPhone)
                    .setFrom(userPhone)
                    .setBody(binding.messageEt.text.toString())
                    .build(),
                argument.otherUserPhone,
                Instant.now().toEpochMilli()
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
