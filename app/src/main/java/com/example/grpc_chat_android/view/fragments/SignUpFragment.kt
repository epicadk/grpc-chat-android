package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.grpc_chat_android.databinding.FragmentSignupBinding
import com.example.grpc_chat_android.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding
        get() = _binding!!
    val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        binding.btSignup.setOnClickListener {
            viewModel.signUp(
                binding.signupEtPhoneNumber.text.toString(),
                binding.signupEtPassword.text.toString()
            )
        }
        viewModel.message.observe(viewLifecycleOwner, {
            // Temporary workaround with string matching
            when (it) {
                "Great Success" -> {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
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
}
