package com.example.ordermanagementcake.data.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

/**
 * Utility class to handle image persistence by copying files to internal storage.
 */
object ImageHelper {
    private const val TAG = "ImageHelper"
    private const val FOLDER_NAME = "cake_images"

    /**
     * Copies an image from a URI (Gallery) to the app's internal private storage.
     * Returns the absolute path of the saved file, or null if it fails.
     */
    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri) ?: return null
            
            val file = createInternalFile(context)
            val outputStream = FileOutputStream(file)
            
            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            Log.d(TAG, "Image saved successfully to: ${file.absolutePath}")
            file.absolutePath
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save image from URI", e)
            null
        }
    }

    /**
     * Saves a Bitmap (Camera Preview) to the app's internal private storage.
     */
    fun saveBitmapToInternalStorage(context: Context, bitmap: Bitmap): String? {
        return try {
            val file = createInternalFile(context)
            val outputStream = FileOutputStream(file)
            
            outputStream.use { output ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output)
                output.flush()
            }

            Log.d(TAG, "Bitmap saved successfully to: ${file.absolutePath}")
            file.absolutePath
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save bitmap", e)
            null
        }
    }

    private fun createInternalFile(context: Context): File {
        val folder = File(context.filesDir, FOLDER_NAME)
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val fileName = "cake_${UUID.randomUUID()}.jpg"
        return File(folder, fileName)
    }

    /**
     * Optional: Deletes an image file if it's no longer needed.
     */
    fun deleteImage(path: String?): Boolean {
        if (path == null) return false
        return try {
            val file = File(path)
            if (file.exists()) {
                file.delete()
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
