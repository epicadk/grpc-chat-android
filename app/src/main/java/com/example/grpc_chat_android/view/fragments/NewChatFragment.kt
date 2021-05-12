package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.grpc_chat_android.databinding.FragmentNewChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewChatFragment : Fragment() {
    private var _binding: FragmentNewChatBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewChatBinding.inflate(layoutInflater, container, false)

        binding.btAddUser.setOnClickListener {
            findNavController().navigate(
                NewChatFragmentDirections.actionNewChatFragmentToMessageListFragment(
                    binding.etAddUser.text.toString()
                )
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
