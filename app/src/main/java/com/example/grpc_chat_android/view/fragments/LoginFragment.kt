package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.grpc_chat_android.PreferenceManager
import com.example.grpc_chat_android.databinding.FragmentLoginBinding
import com.example.grpc_chat_android.models.Chat
import com.example.grpc_chat_android.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SignInViewModel by viewModels()
    @Inject
    lateinit var preferenceManager: PreferenceManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        runBlocking {
            val authToken = preferenceManager.getToken()
            if (!authToken.isNullOrBlank()) {
                login()
            }
        }

        binding.btSignup.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        binding.loginEtPassword.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    binding.btLogin.callOnClick()
                    true
                }
                else -> false
            }
        }

        binding.btLogin.setOnClickListener {
            viewModel.login(
                Chat.LoginRequest.newBuilder()
                    .setPhonenumber(binding.loginEtPhone.text.toString())
                    .setPassword(binding.loginEtPassword.text.toString()).build()
            )
        }

        viewModel.message.observe(viewLifecycleOwner, {
            // Temporary workaround with string matching
            when (it) {
                "Login Successful" -> {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    login()
                    // Delete chats only on new login
                    viewModel.deleteAll()
                }
                else -> Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
        activity?.finish()
    }
}
