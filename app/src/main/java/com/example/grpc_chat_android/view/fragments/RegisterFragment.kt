package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.grpc_chat_android.databinding.FragmentRegisterBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.viewmodel.LoginViewModel
import com.example.grpc_chat_android.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var key: Preferences.Key<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btSignup.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToSignUpFragment())
        }
        binding.btRegister.setOnClickListener {
            activityViewModel.login(
                Chat.LoginRequest.newBuilder().setPhonenumber(binding.registerEtPhoneNumber.text.toString())
                    .setPassword(binding.registerEtPassword.text.toString()).build()
            )
            lifecycleScope.launch {
                viewModel.saveUser(binding.registerEtPhoneNumber.text.toString(), requireContext(), key)
            }
            findNavController()
                .navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToChatListFragment(
                        binding.registerEtPhoneNumber.text.toString()
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
