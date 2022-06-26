package com.karry.chat_feature.presentation.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.karry.base.BaseFragment
import com.karry.chat_feature.R
import com.karry.chat_feature.databinding.FragmentChatBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChatFragment : BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChatBinding
        get() = FragmentChatBinding::inflate


    override fun prepareView(savedInstanceState: Bundle?) {
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)
        }
    }
}