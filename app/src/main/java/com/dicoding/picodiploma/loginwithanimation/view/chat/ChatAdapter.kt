package com.dicoding.picodiploma.loginwithanimation.view.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.MessageResponse
import com.squareup.picasso.Picasso

class ChatAdapter(
    private val receiverProfileImageUrl: String,
    private val currentUserId: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val messages = mutableListOf<MessageResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun setMessages(newMessages: List<MessageResponse>) {
        messages.clear()
        messages.addAll(newMessages)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].sender.id == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_sent_message, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_container_received_message, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder is SentMessageViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedMessageViewHolder) {
            holder.bind(message)
        }
    }

    override fun getItemCount() = messages.size

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView = itemView.findViewById(R.id.textMessage)
        private val textDateTime: TextView = itemView.findViewById(R.id.textDateTime)

        fun bind(message: MessageResponse) {
            textMessage.text = message.message
            textDateTime.text = message.createdAt
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageProfile: ImageView = itemView.findViewById(R.id.imageProfile)
        private val textMessage: TextView = itemView.findViewById(R.id.textMessage)
        private val textDateTime: TextView = itemView.findViewById(R.id.textDateTime)

        fun bind(message: MessageResponse) {
            Picasso.get().load(receiverProfileImageUrl).into(imageProfile)
            textMessage.text = message.message
            textDateTime.text = message.createdAt
        }
    }

    companion object {
        private const val VIEW_TYPE_SENT = 1
        private const val VIEW_TYPE_RECEIVED = 2
    }
}
