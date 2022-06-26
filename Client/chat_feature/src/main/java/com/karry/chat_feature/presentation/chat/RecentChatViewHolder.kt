package com.karry.chat_feature.presentation.chat

import com.karry.base.BaseViewHolder
import com.karry.chat_feature.databinding.ItemRecentChatListBinding
import com.karry.data.models.RecentChat

class RecentChatViewHolder constructor(
    private val binding: ItemRecentChatListBinding,
    private val click: ((RecentChat?) -> Unit)? = null
): BaseViewHolder<RecentChat, ItemRecentChatListBinding>(binding) {
    init {
        binding.root.setOnClickListener {
            click?.invoke(getRowItem())
        }
    }

    override fun bind() {
        getRowItem()?.let {
            with(binding) {
                recentName.text = it.receiverName
                recentMess.text = it.lastMessage
                recentTime.text = it.timestamp.toString()
            }
        }
    }
}