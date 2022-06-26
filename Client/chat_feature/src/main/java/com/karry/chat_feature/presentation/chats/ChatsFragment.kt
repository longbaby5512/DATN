package com.karry.chat_feature.presentation.chats

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.karry.base.BaseFragment
import com.karry.chat_feature.R
import com.karry.chat_feature.databinding.FragmentChatsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ChatsFragment : BaseFragment<FragmentChatsBinding>(R.layout.fragment_chats) {

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChatsBinding
        get() = FragmentChatsBinding::inflate


    override fun prepareView(savedInstanceState: Bundle?) {

        binding.fabNewChat.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab_new_chat)
                .setAction("Action", null).show()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.rvChatList.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY + 12 && binding.fabNewChat.isExtended) {
                    binding.fabNewChat.shrink()
                }

                // the delay of the extension of the FAB is set for 12 items
                if (scrollY < oldScrollY - 12 && !binding.fabNewChat.isExtended) {
                    binding.fabNewChat.extend()
                }

                // if the nestedScrollView is at the first item of the list then the
                // extended floating action should be in extended state
                if (scrollY == 0) {
                    binding.fabNewChat.extend()
                }
            }
        }
    }
}