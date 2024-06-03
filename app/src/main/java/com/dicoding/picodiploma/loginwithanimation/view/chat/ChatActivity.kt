package com.dicoding.picodiploma.loginwithanimation.view.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.response.MessageResponse
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityChatBinding
import com.dicoding.picodiploma.loginwithanimation.view.MessageViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.contact.ContactActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import com.squareup.picasso.Picasso

class ChatActivity : AppCompatActivity() {
    private val viewModel: ChatViewModel by viewModels {
        MessageViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var senderId: String
    private lateinit var receiverId: String
    private var conversationId: String? = null
//    private var message: String = ""
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val picture = intent.getStringExtra("picture")
        val name = intent.getStringExtra("name")
        receiverId = intent.getStringExtra(RECEIVER_ID) ?: ""

        Picasso.get().load(picture).into(binding.imageInfo)
        binding.textName.text = name
        progressBar = binding.progressBar4

        setupAction()
        observeSession()
    }

    private fun observeSession(){
        viewModel.getSession().observe(this) { user ->
            if (user == null || !user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                user.accessToken.let { token ->
                    senderId = user.id
                    viewModel.getConversations()
                    observeViewModel()
                    setupRecyclerView()
                    Log.d("ChatActivity", "Fetching contact with token: $token")
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val receiverProfileImageUrl = intent.getStringExtra("picture") ?: ""
        chatAdapter = ChatAdapter(receiverProfileImageUrl, senderId)
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }

    private fun setupAction() {
        binding.buttonSend.setOnClickListener {
            val message = binding.inputMessage.text.toString()
            if (message.isNotEmpty() && conversationId != null) {
                viewModel.sendMessage(conversationId!!, message)
                binding.inputMessage.text.clear()
                viewModel.getMessages(conversationId!!)
            }
        }
        binding.imageBack.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.messages.observe(this) { messages ->
            updateChatMessages(messages)
        }

        viewModel.conversations.observe(this) { conversations ->
            conversations?.let {
                Log.d("ChatActivity", "Number of conversations: ${it.size}")
                val existingConversation = it.find { convo ->
                    convo.users.any { user -> user.id == receiverId }
                }
                if (existingConversation != null) {
                    conversationId = existingConversation.id
                    viewModel.getMessages(conversationId!!)
                } else {
                    viewModel.createConversation(receiverId)
                }
            }
        }

        viewModel.newConversation.observe(this) { newConversation ->
            newConversation?.let {
                conversationId = it.id
                viewModel.getMessages(conversationId!!)
            }
        }

        viewModel.sendMessageResponse.observe(this) { response ->
            response?.let {
                val newMessages = viewModel.messages.value.orEmpty().toMutableList()
                newMessages.add(response)
                updateChatMessages(newMessages)
                binding.chatRecyclerView.scrollToPosition(newMessages.size - 1)
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Log.e("ChatActivity", "Error: $errorMessage")
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateChatMessages(messages: List<MessageResponse>) {
        chatAdapter.setMessages(messages)
        chatAdapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val RECEIVER_ID = "receiver_id"
    }
}