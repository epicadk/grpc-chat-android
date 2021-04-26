package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.grpc_chat_android.databinding.FragmentRegisterBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btRegister.setOnClickListener {
            viewModel.login(
                Chat.LoginRequest.newBuilder().setUsername(binding.registerEt.text.toString())
                    .build()
            )
            // TODO store this in protoDataStore or shared pref
            findNavController().navigate(
                RegisterFragmentDirections.actionRegisterFragmentToChatListFragment(
                    binding.registerEt.text.toString()
                )
            )
            viewModel.deleteAll()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
