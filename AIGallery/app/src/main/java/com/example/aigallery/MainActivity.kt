// In MainActivity.kt
package com.example.aigallery

import android.Manifest
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.aigallery.ui.theme.AIGalleryTheme

class MainActivity : ComponentActivity() {

    private var newImageObserver: ContentObserver? = null

    // --- NEW: Handle permission requests ---
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your app.
                registerNewImageObserver()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied.
                Toast.makeText(this, "Permission denied. Cannot sync new photos.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- NEW: Check for permissions before registering the observer ---
        checkAndRequestPermissions()

        setContent {
            AIGalleryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GalleryScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                registerNewImageObserver()
            }
            else -> {
                // You can directly ask for the permission.
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun registerNewImageObserver() {
        newImageObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                super.onChange(selfChange, uri)
                uri?.let {
                    // A new image was detected! Let's start the upload worker.
                    startUploadWorker(it)
                    Toast.makeText(this@MainActivity, "New image detected! Starting upload...", Toast.LENGTH_SHORT).show()
                }
            }
        }

        contentResolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true,
            newImageObserver!!
        )
    }

    private fun startUploadWorker(imageUri: Uri) {
        val inputData = Data.Builder()
            .putString("image_uri", imageUri.toString())
            .build()

        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        newImageObserver?.let { contentResolver.unregisterContentObserver(it) }
    }
}