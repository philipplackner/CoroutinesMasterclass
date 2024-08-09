package com.plcoding.coroutinesmasterclass.sections.coroutine_cancellation.trap3

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.coroutineContext

class FileManager(private val context: Context) {
    private var tempFile: File? = null

    suspend fun writeRecordsToFile() {
        println("Writing records to file...")
        withContext(Dispatchers.IO) {
            tempFile = File.createTempFile("db_records_", ".txt", context.cacheDir)
            try {
                repeat(5) {
                    println("Writing line $it & isActive: ${coroutineContext.isActive}")
                    tempFile?.appendText("Database record $it\n")
                    delay(1000)
                }
            } catch (e: Exception) {
                ensureActive()
                println("Error writing records to file: $e")
            } finally {
                println("Cleaning up temp file...")
                withContext(NonCancellable) {
                    cleanupTempFile()
                }
                println("Cleaned up temp file!")
            }
        }
    }

    private suspend fun cleanupTempFile() {
        println("Cleanup function, is active: ${coroutineContext.isActive}")
        withContext(Dispatchers.IO) {
            delay(2000)
            tempFile?.let { file ->
                if(file.exists()) {
                    file.delete()
                    println("Deleted file")
                }
            }
        }
    }
}