package com.dicoding.picodiploma.loginwithanimation.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.addstory.AddStoryActivity
import com.dicoding.picodiploma.loginwithanimation.view.contact.ContactActivity
import com.dicoding.picodiploma.loginwithanimation.view.detailprofile.DetailProfileActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar2

        viewModel.userSession.observe(this) { user ->
            if (user == null || !user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                val token = user.accessToken
                if (token.isNotEmpty()) {
//                    viewModel.getStory()
                    Log.d("MainActivity", "Fetching stories with token: $token")
                } else {
                    Log.e("MainActivity", "Token is null or empty")
                }
            }
        }


        setupView()
        setupRecyclerView()
        setupAction()
        playAnimation()

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

//        viewModel.storyResponse.observe(this) { stories ->
//            adapter.submitList(stories)
//            sendStoryDataToWidget(stories)
//        }

        viewModel.error.observe(this) { errorMessage ->
            Log.e("MainActivity", "Error: $errorMessage")
        }

        viewModel.getSession().observe(this) { user ->
            if (user == null || !user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                user.accessToken.let { token ->
//                    viewModel.getStory()
                    Log.d("MainActivity", "Fetching stories with token: $token")
                }
            }
        }

        window.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.sharedElementExitTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
    }

//    private fun sendStoryDataToWidget(stories: List<ListStoryItem>) {
//        val intent = Intent(this, StoryWidget::class.java)
//        intent.action = StoryWidget.ACTION_UPDATE_STORIES
//        intent.putParcelableArrayListExtra(StoryWidget.EXTRA_STORIES, ArrayList(stories))
//        sendBroadcast(intent)
//    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupRecyclerView() {
        adapter = StoryAdapter()
        binding.usersRecycleView.layoutManager = LinearLayoutManager(this)
        binding.usersRecycleView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        binding.usersRecycleView.adapter = adapter
    }

    private fun setupAction() {
        binding.actionChat.setOnClickListener {
            startActivity(Intent(this, ContactActivity::class.java))
        }
        binding.addStory.setOnClickListener{
            startActivity(Intent(this, AddStoryActivity::class.java))
            finish()
        }
        binding.actionProfile.setOnClickListener{
            startActivity(Intent(this, DetailProfileActivity::class.java))
        }
    }

    private fun playAnimation() {
        val name = ObjectAnimator.ofFloat(binding.terraVision, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.frameLayout, View.ALPHA, 1f).setDuration(500)
        val logout = ObjectAnimator.ofFloat(binding.addStory, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(name, message, logout)
            startDelay = 500
        }.start()
    }
}
