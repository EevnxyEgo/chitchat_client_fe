package com.dicoding.picodiploma.loginwithanimation.view.detailstory

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.data.response.Story
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class DetailStoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDetailStoryBinding
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id") ?: ""

//        setupObservers()
        setupAction()

//        id?.let {
//            viewModel.getDetailStories(it)
//        }
        window.sharedElementEnterTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.sharedElementExitTransition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
    }

    private fun setupAction() {
        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

//    private fun setupObservers() {
//        viewModel.detailStoryResponse.observe(this) { story ->
//            story?.let { displayStoryDetail(it) }
//        }
//        viewModel.isLoading.observe(this) { isLoading ->
//            binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//        viewModel.error.observe(this) { errorMessage ->
//            errorMessage?.let { showErrorSnackbar(it) }
//        }
//    }

    private fun displayStoryDetail(story: Story) {
        binding.apply {
            Picasso.get().load(story.photoUrl).into(ivDetailPhoto)
            tvId.text = story.id ?: ""
            tvDetailName.text = story.name ?: ""
            tvDetailDescription.text = story.description ?: ""
            tvcreatedAt.text = story.createdAt ?: ""
            tvlon.text = story.lon?.toString() ?: ""
            tvlat.text = story.lat?.toString() ?: ""
        }
    }

    private fun showErrorSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}