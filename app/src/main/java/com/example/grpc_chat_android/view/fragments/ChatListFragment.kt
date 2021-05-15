package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grpc_chat_android.R
import com.example.grpc_chat_android.databinding.FragmentChatListBinding
import com.example.grpc_chat_android.di.dataStore
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.view.activities.MainActivity
import com.example.grpc_chat_android.view.adapter.ChatAdapter
import com.example.grpc_chat_android.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatListFragment : Fragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ChatViewModel by viewModels()

    @Inject
    lateinit var key: Preferences.Key<String>
    private var userPhone: String? = ""
    private val chatAdapter: ChatAdapter by lazy { ChatAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            userPhone = requireContext().dataStore.data.map { it[key] }.first()
        }
        viewModel.connect(Chat.Phone.newBuilder().setPhonenumber(userPhone).build())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar).navigationIcon = null
        binding.fbAddChat.setOnClickListener {
            findNavController()
                .navigate(ChatListFragmentDirections.actionChatListFragmentToNewChatFragment())
        }
        binding.rvChatList.layoutManager = LinearLayoutManager(context)
        binding.rvChatList.addItemDecoration(
            DividerItemDecoration(
                context,
                (binding.rvChatList.layoutManager as LinearLayoutManager).orientation
            )
        )
        viewModel.allChatLiveData.observe(viewLifecycleOwner, { chatAdapter.setData(it) })
        binding.rvChatList.adapter = chatAdapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
