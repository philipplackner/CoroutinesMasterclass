package com.plcoding.coroutinesmasterclass.util

import android.graphics.Bitmap

object PhotoProcessor {
    fun findDominantColor(image: Bitmap): Int {
        val colorCounts = mutableMapOf<Int, Int>()
        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val pixelColor = image.getPixel(x, y)
                colorCounts[pixelColor] = colorCounts.getOrDefault(pixelColor, 0) + 1
            }
        }
        return colorCounts.entries.sortedByDescending { it.value }
            .take(1)
            .map { it.key }
            .first()
    }
}