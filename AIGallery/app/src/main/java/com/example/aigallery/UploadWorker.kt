// In a new file: UploadWorker.kt
package com.example.aigallery

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class UploadWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Get the image URI passed from the ContentObserver
        val imageUriString = inputData.getString("image_uri") ?: return Result.failure()
        val imageUri = Uri.parse(imageUriString)

        return try {
            // Use the ContentResolver to get an InputStream for the image
            context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                // Read the bytes of the image
                val imageBytes = inputStream.readBytes()
                val requestBody = imageBytes.toRequestBody("image/jpeg".toMediaTypeOrNull())

                // Create the multipart request part
                val multipartBody = MultipartBody.Part.createFormData(
                    "image",
                    "new_image.jpg", // Filename on the server
                    requestBody
                )

                // Make the API call using Retrofit
                RetrofitClient.apiService.addImage(multipartBody)
            }
            // If everything succeeds
            Result.success()
        } catch (e: Exception) {
            // If there's an error (e.g., network issue), WorkManager can retry later
            Result.retry()
        }
    }
}