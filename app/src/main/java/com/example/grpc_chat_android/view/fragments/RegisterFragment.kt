package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.grpc_chat_android.databinding.FragmentRegisterBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: LoginViewModel by activityViewModels()

    @Inject
    lateinit var key: Preferences.Key<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btRegister.setOnClickListener {
            viewModel.login(
                Chat.LoginRequest.newBuilder().setPhonenumber(binding.registerEt.text.toString())
                    .build()
            )
            lifecycleScope.launch {
                viewModel.saveUser(binding.registerEt.text.toString(), requireContext(), key)
            }
            findNavController()
                .navigate(
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
