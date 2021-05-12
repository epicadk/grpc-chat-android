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
import com.example.grpc_chat_android.databinding.FragmentLoginBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.viewmodel.MainActivityViewModel
import com.example.grpc_chat_android.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SignInViewModel by viewModels()
    private val activityViewModel: MainActivityViewModel by activityViewModels()

    @Inject
    lateinit var key: Preferences.Key<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.btSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
        binding.btRegister.setOnClickListener {
            activityViewModel.login(
                Chat.LoginRequest.newBuilder()
                    .setPhonenumber(binding.registerEtPhoneNumber.text.toString())
                    .setPassword(binding.registerEtPassword.text.toString()).build()
            )
            lifecycleScope.launch {
                viewModel.saveUserPhone(
                    binding.registerEtPhoneNumber.text.toString(),
                    requireContext(),
                    key
                )
                findNavController()
                    .navigate(
                        LoginFragmentDirections.actionLoginFragmentToChatListFragment(
                            binding.registerEtPhoneNumber.text.toString()
                        )
                    )
            }
            viewModel.deleteAll()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
