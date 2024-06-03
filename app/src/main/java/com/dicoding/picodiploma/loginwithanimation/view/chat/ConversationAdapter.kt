//package com.dicoding.picodiploma.loginwithanimation.view.chat
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.dicoding.picodiploma.loginwithanimation.R
//import com.dicoding.picodiploma.loginwithanimation.data.response.ConversationResponse
//import com.squareup.picasso.Picasso
//
//class ConversationAdapter(
//    private val conversations: List<ConversationResponse>,
//    private val receiverProfileImageUrl: String,
//    private val listener: OnConversationClickListener
//) : RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {
//
//    interface OnConversationClickListener {
//        fun onConversationClick(conversation: ConversationResponse)
//    }
//
//    inner class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(conversation: ConversationResponse) {
//            itemView.conversation_name.text = conversation.name
//            itemView.latest_message.text = conversation.latestMessage.message
//            Picasso.get().load(conversation.picture).into(itemView.)
//            itemView.setOnClickListener {
//                listener.onConversationClick(conversation)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
//        return ConversationViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
//        holder.bind(conversations[position])
//    }
//
//    override fun getItemCount(): Int = conversations.size
//}