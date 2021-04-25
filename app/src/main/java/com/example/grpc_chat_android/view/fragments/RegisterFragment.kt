package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grpc_chat_android.databinding.FragmentRegisterBinding
import com.example.grpc_chat_android.view.activities.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // TODO store this in protoDataStore or shared pref
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btRegister.setOnClickListener {
            MainActivity.user = binding.registerEt.text.toString()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}