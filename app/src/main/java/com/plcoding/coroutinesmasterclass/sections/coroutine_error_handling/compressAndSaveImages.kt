package com.plcoding.coroutinesmasterclass.sections.coroutine_error_handling

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.plcoding.coroutinesmasterclass.sections.coroutine_contexts.main_safety.BitmapCompressor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

suspend fun saveBitmap(context: Context, bitmap: Bitmap) {
    withContext(Dispatchers.IO) {
        val bytes = ByteArrayOutputStream().use { byteStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
            yield()

            byteStream.toByteArray()
        }

        yield()

        val file = File(context.filesDir, "${bitmap.hashCode()}.jpg")
        FileOutputStream(file).use { stream ->
            stream.write(bytes)
        }
    }
}

suspend fun compressAndSaveImages(
    context: Context,
    images: List<Uri>,
    compressor: BitmapCompressor
) {
    supervisorScope {
        images.forEach { uri ->
            launch {
                compressor.compressImage(
                    contentUri = uri,
                    compressionThreshold = 1024L
                )?.let { bitmap ->
                    saveBitmap(context, bitmap)
                }
            }
        }
    }
}