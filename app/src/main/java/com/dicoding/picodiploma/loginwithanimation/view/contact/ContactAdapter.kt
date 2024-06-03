package com.dicoding.picodiploma.loginwithanimation.view.contact

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.data.response.ResponseItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemContactBinding
import com.dicoding.picodiploma.loginwithanimation.view.chat.ChatActivity
import com.squareup.picasso.Picasso

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    private var items: List<Any> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (val item = items[position]) {
            is ResponseItem -> {
                holder.bind(item)
                Log.d("StoryAdapter", "Binding item at position $position with data: ${item.name}")
            }
            else -> throw IllegalArgumentException("Unsupported item type")
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<ResponseItem>) {
        Log.d("StoryAdapter", "Submitting new list with size: ${newItems.size}")
        val diffResult = DiffUtil.calculateDiff(ReviewDiffCallback(items, newItems))
        this.items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private class ReviewDiffCallback(
        private val oldList: List<Any>,
        private val newList: List<Any>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return when {
                oldItem is ResponseItem && newItem is ResponseItem -> oldItem.id == newItem.id

                else -> false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }

    inner class MyViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseItem) {
            Picasso.get().load(item.picture).into(binding.imageProfile)
            binding.textName.text = item.name
            binding.textEmail.text = item.email
            binding.root.setOnClickListener {
                val intentDetail = Intent(binding.root.context, ChatActivity::class.java)
                intentDetail.putExtra(ChatActivity.RECEIVER_ID, item.id)
                intentDetail.putExtra("picture", item.picture)
                intentDetail.putExtra("name", item.name)
                binding.root.context.startActivity(intentDetail)
            }
        }
    }
}