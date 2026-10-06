package com.example.danishspelling.service.ocr

import android.graphics.Bitmap

/**
 * Abstraction for OCR (text recognition) engines.
 * Implementations can use Google ML Kit, Xiaomi OCR, or any other engine.
 */
interface OCREngine {
    /**
     * Perform text recognition on the given bitmap.
     * @param bitmap The image to extract text from.
     * @param onSuccess Called with the extracted text on success.
     * @param onFailure Called with the exception on failure.
     */
    fun recognizeText(
        bitmap: Bitmap,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    )
}
