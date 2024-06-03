package com.dicoding.picodiploma.loginwithanimation.view.addstory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityAddStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity

@Suppress("DEPRECATION")
class AddStoryActivity : AppCompatActivity() {
    private val viewModel by viewModels<AddStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Permission request granted")
            } else {
                showToast("Permission request denied")
            }
        }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        if (savedInstanceState != null) {
            currentImageUri = savedInstanceState.getParcelable(IMAGE_URI_KEY)
            currentImageUri?.let {
                binding.previewImageView.setImageURI(it)
            }
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.imageBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
        binding.buttonAdd.setOnClickListener {
//            uploadImage()
        }
    }

//    private fun uploadImage() {
//        currentImageUri?.let { uri ->
//            val imageFile = uriToFile(uri, this).reduceFileImage()
//            Log.d("Image File", "showImage: ${imageFile.path}")
//            val description = binding.edAddDescription.text.toString().trim()
//            viewModel.uploadImage(imageFile, description).observe(this) { result ->
//                if (result != null) {
//                    when (result) {
//                        is ResultState.Loading -> {
//                            showLoading(true)
//                        }
//
//                        is ResultState.Success -> {
//                            result.data.message?.let { showToast(it) }
//                            showLoading(false)
//                            val intent = Intent(this, MainActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                            startActivity(intent)
//                            finish()
//                        }
//
//                        is ResultState.Error -> {
//                            showToast(result.error)
//                            showLoading(false)
//                        }
//                    }
//                }
//            }
//        } ?: showToast(getString(R.string.empty_image_warning))
//    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar4.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(IMAGE_URI_KEY, currentImageUri)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val IMAGE_URI_KEY = "IMAGE_URI_KEY"
    }
}