package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grpc_chat_android.PreferenceManager
import com.example.grpc_chat_android.R
import com.example.grpc_chat_android.databinding.FragmentChatListBinding
import com.example.grpc_chat_android.view.adapter.ChatAdapter
import com.example.grpc_chat_android.viewmodel.ChatViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatListFragment : Fragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: ChatViewModel by activityViewModels()
    private val chatAdapter: ChatAdapter by lazy { ChatAdapter() }

    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatListBinding.inflate(inflater, container, false)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                showLogoutDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Log Out")
            .setMessage("Do you wish to sign out from your account?")
            .setPositiveButton("Confirm") { dialog, _ ->
                lifecycleScope.launch {
                    preferenceManager.saveToken("")
                    dialog.dismiss()
                    findNavController().navigate(ChatListFragmentDirections.actionChatListFragmentToAuthActivity())
                    activity?.finish()
                }
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }.show()
    }
}
