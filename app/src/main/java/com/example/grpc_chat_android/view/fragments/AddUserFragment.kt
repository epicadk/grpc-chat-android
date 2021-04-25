package com.example.grpc_chat_android.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.grpc_chat_android.databinding.FragmentAddUserBinding

class AddUserFragment : DialogFragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddUserBinding.inflate(layoutInflater, container, false)
        binding.btAddUser.setOnClickListener {
            this.findNavController().navigate(
                AddUserFragmentDirections.actionAddUserFragment2ToMessageListFragment(binding.etAddUser.text.toString())
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}