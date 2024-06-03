package com.dicoding.picodiploma.loginwithanimation.view.contact

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityContactBinding
import com.dicoding.picodiploma.loginwithanimation.view.MessageViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView

class ContactActivity : AppCompatActivity() {
    private val viewModel by viewModels<ContactViewModel> {
        MessageViewModelFactory.getInstance(this)
    }
    private lateinit var adapter: ContactAdapter
    private lateinit var searchAdapter: ContactAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityContactBinding
    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView
    private lateinit var searchRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBar3
        searchBar = binding.searchBar
        searchView = binding.searchView
        searchRecyclerView = binding.searchView.findViewById(R.id.searchRecyclerView)

        viewModel.userSession.observe(this) { user ->
            if (user == null || !user.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                val token = user.accessToken
                if (token.isNotEmpty()) {
                    viewModel.getAllUsers()
                    Log.d("ContactActivity", "Fetching contact with token: $token")
                } else {
                    Log.e("ContactActivity", "Token is null or empty")
                }
            }
        }

        setupView()
        setupRecyclerView()
        setupAction()
        setupSearchRecyclerView()

        viewModel.contactResponse.observe(this) { contacts ->
            adapter.submitList(contacts)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            Log.e("ContactActivity", "Error: $errorMessage")
        }

        viewModel.getSession().observe(this) { user ->
            if (user == null || !user.isLogin) {
                startActivity(Intent(this, ContactActivity::class.java))
                finish()
            } else {
                user.accessToken.let { token ->
                    viewModel.getAllUsers()
                    Log.d("ContactActivity", "Fetching contact with token: $token")
                }
            }
        }

        setupSearch()
    }

    private fun setupSearch() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val query = s?.toString()?.trim() ?: ""
                    if (query.isNotEmpty()) {
                        viewModel.searchUsers(query)
                    } else {
                        viewModel.getAllUsers() // Jika tidak ada keyword, tampilkan semua user
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })
        }

        viewModel.contactResponse.observe(this) { contacts ->
            searchAdapter.submitList(contacts)
        }
    }

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
        adapter = ContactAdapter()
        binding.usersRecycleView2.layoutManager = LinearLayoutManager(this)
        binding.usersRecycleView2.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        binding.usersRecycleView2.adapter = adapter
    }

    private fun setupSearchRecyclerView() {
        searchAdapter = ContactAdapter()
        searchRecyclerView.layoutManager = LinearLayoutManager(this)
        searchRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        searchRecyclerView.adapter = searchAdapter
    }

    private fun setupAction() {
        binding.imageBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
