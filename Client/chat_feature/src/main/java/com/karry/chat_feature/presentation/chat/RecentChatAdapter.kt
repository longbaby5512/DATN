package com.karry.chat_feature.presentation.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.karry.base.BaseRecyclerAdapter
import com.karry.chat_feature.databinding.ItemRecentChatListBinding
import com.karry.data.models.RecentChat

class RecentChatAdapter constructor(
    private val click: ((RecentChat?) -> Unit)? = null
): BaseRecyclerAdapter<RecentChat, ItemRecentChatListBinding, RecentChatViewHolder>(RecentChatItemDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChatViewHolder {
        val binding = ItemRecentChatListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecentChatViewHolder(binding, click)
    }
}

class RecentChatItemDiffUtil: DiffUtil.ItemCallback<RecentChat>() {
    override fun areItemsTheSame(oldItem: RecentChat, newItem: RecentChat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecentChat, newItem: RecentChat): Boolean {
        return oldItem == newItem
    }
}