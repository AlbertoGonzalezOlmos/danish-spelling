package com.example.danishspelling.presentation.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CropImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bitmap: Bitmap? = null
    private val displayMatrix = Matrix()
    private val inverseMatrix = Matrix()
    private val imageRect = RectF()
    private val cropRect = RectF()

    private var activeHandle = Handle.NONE
    private var lastTouchX = 0f
    private var lastTouchY = 0f

    private val density = resources.displayMetrics.density
    private val handleRadiusPx = 12f * density
    private val touchSlopPx = 24f * density
    private val minCropSizePx = 48f * density

    private val overlayPaint = Paint().apply {
        color = Color.argb(160, 0, 0, 0)
        style = Paint.Style.FILL
    }

    private val borderPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 2f * density
        isAntiAlias = true
    }

    private val handlePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val gridPaint = Paint().apply {
        color = Color.argb(128, 255, 255, 255)
        style = Paint.Style.STROKE
        strokeWidth = 1f * density
    }

    private val overlayPath = Path()

    private enum class Handle {
        NONE,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP_EDGE, BOTTOM_EDGE, LEFT_EDGE, RIGHT_EDGE,
        CENTER
    }

    fun setBitmap(newBitmap: Bitmap) {
        val previousWidth = bitmap?.width
        val previousHeight = bitmap?.height
        bitmap = newBitmap

        if (previousWidth != newBitmap.width || previousHeight != newBitmap.height) {
            if (width > 0 && height > 0) {
                recalculateImageBounds()
            } else {
                requestLayout()
            }
        } else {
            invalidate()
        }
    }

    fun resetCropRect() {
        cropRect.set(imageRect)
        invalidate()
    }

    fun getCropRect(): Rect {
        val bmp = bitmap ?: return Rect()

        val points = floatArrayOf(
            cropRect.left, cropRect.top,
            cropRect.right, cropRect.bottom
        )
        inverseMatrix.mapPoints(points)

        val left = points[0].toInt().coerceIn(0, bmp.width)
        val top = points[1].toInt().coerceIn(0, bmp.height)
        val right = points[2].toInt().coerceIn(0, bmp.width)
        val bottom = points[3].toInt().coerceIn(0, bmp.height)

        return Rect(left, top, right, bottom)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateImageBounds()
    }

    private fun recalculateImageBounds() {
        val bmp = bitmap ?: return
        val srcRect = RectF(0f, 0f, bmp.width.toFloat(), bmp.height.toFloat())
        val dstRect = RectF(0f, 0f, width.toFloat(), height.toFloat())

        displayMatrix.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.CENTER)
        displayMatrix.invert(inverseMatrix)

        imageRect.set(srcRect)
        displayMatrix.mapRect(imageRect)

        resetCropRect()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bmp = bitmap ?: return

        // Draw bitmap
        canvas.drawBitmap(bmp, displayMatrix, null)

        // Draw darkened overlay outside crop rect
        overlayPath.reset()
        overlayPath.addRect(0f, 0f, width.toFloat(), height.toFloat(), Path.Direction.CW)
        overlayPath.addRect(cropRect, Path.Direction.CCW)
        canvas.drawPath(overlayPath, overlayPaint)

        // Draw crop rect border
        canvas.drawRect(cropRect, borderPaint)

        // Draw rule-of-thirds grid
        val thirdW = cropRect.width() / 3f
        val thirdH = cropRect.height() / 3f
        for (i in 1..2) {
            canvas.drawLine(
                cropRect.left + thirdW * i, cropRect.top,
                cropRect.left + thirdW * i, cropRect.bottom,
                gridPaint
            )
            canvas.drawLine(
                cropRect.left, cropRect.top + thirdH * i,
                cropRect.right, cropRect.top + thirdH * i,
                gridPaint
            )
        }

        // Draw corner handles
        val corners = arrayOf(
            cropRect.left to cropRect.top,
            cropRect.right to cropRect.top,
            cropRect.left to cropRect.bottom,
            cropRect.right to cropRect.bottom
        )
        for ((cx, cy) in corners) {
            canvas.drawCircle(cx, cy, handleRadiusPx, handlePaint)
        }

        // Draw edge midpoint handles
        val midRadius = handleRadiusPx * 0.6f
        val edgeMids = arrayOf(
            (cropRect.left + cropRect.right) / 2f to cropRect.top,
            (cropRect.left + cropRect.right) / 2f to cropRect.bottom,
            cropRect.left to (cropRect.top + cropRect.bottom) / 2f,
            cropRect.right to (cropRect.top + cropRect.bottom) / 2f
        )
        for ((cx, cy) in edgeMids) {
            canvas.drawCircle(cx, cy, midRadius, handlePaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                activeHandle = detectHandle(x, y)
                if (activeHandle != Handle.NONE) {
                    lastTouchX = x
                    lastTouchY = y
                    parent?.requestDisallowInterceptTouchEvent(true)
                    return true
                }
                return false
            }

            MotionEvent.ACTION_MOVE -> {
                if (activeHandle == Handle.NONE) return false

                val dx = x - lastTouchX
                val dy = y - lastTouchY

                when (activeHandle) {
                    Handle.TOP_LEFT -> {
                        cropRect.left = (cropRect.left + dx)
                            .coerceIn(imageRect.left, cropRect.right - minCropSizePx)
                        cropRect.top = (cropRect.top + dy)
                            .coerceIn(imageRect.top, cropRect.bottom - minCropSizePx)
                    }
                    Handle.TOP_RIGHT -> {
                        cropRect.right = (cropRect.right + dx)
                            .coerceIn(cropRect.left + minCropSizePx, imageRect.right)
                        cropRect.top = (cropRect.top + dy)
                            .coerceIn(imageRect.top, cropRect.bottom - minCropSizePx)
                    }
                    Handle.BOTTOM_LEFT -> {
                        cropRect.left = (cropRect.left + dx)
                            .coerceIn(imageRect.left, cropRect.right - minCropSizePx)
                        cropRect.bottom = (cropRect.bottom + dy)
                            .coerceIn(cropRect.top + minCropSizePx, imageRect.bottom)
                    }
                    Handle.BOTTOM_RIGHT -> {
                        cropRect.right = (cropRect.right + dx)
                            .coerceIn(cropRect.left + minCropSizePx, imageRect.right)
                        cropRect.bottom = (cropRect.bottom + dy)
                            .coerceIn(cropRect.top + minCropSizePx, imageRect.bottom)
                    }
                    Handle.TOP_EDGE -> {
                        cropRect.top = (cropRect.top + dy)
                            .coerceIn(imageRect.top, cropRect.bottom - minCropSizePx)
                    }
                    Handle.BOTTOM_EDGE -> {
                        cropRect.bottom = (cropRect.bottom + dy)
                            .coerceIn(cropRect.top + minCropSizePx, imageRect.bottom)
                    }
                    Handle.LEFT_EDGE -> {
                        cropRect.left = (cropRect.left + dx)
                            .coerceIn(imageRect.left, cropRect.right - minCropSizePx)
                    }
                    Handle.RIGHT_EDGE -> {
                        cropRect.right = (cropRect.right + dx)
                            .coerceIn(cropRect.left + minCropSizePx, imageRect.right)
                    }
                    Handle.CENTER -> {
                        val w = cropRect.width()
                        val h = cropRect.height()
                        var newLeft = (cropRect.left + dx).coerceIn(imageRect.left, imageRect.right - w)
                        var newTop = (cropRect.top + dy).coerceIn(imageRect.top, imageRect.bottom - h)
                        cropRect.set(newLeft, newTop, newLeft + w, newTop + h)
                    }
                    Handle.NONE -> {}
                }

                lastTouchX = x
                lastTouchY = y
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                activeHandle = Handle.NONE
                parent?.requestDisallowInterceptTouchEvent(false)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun detectHandle(x: Float, y: Float): Handle {
        val slop = touchSlopPx

        // Corners first
        if (isNear(x, y, cropRect.left, cropRect.top, slop)) return Handle.TOP_LEFT
        if (isNear(x, y, cropRect.right, cropRect.top, slop)) return Handle.TOP_RIGHT
        if (isNear(x, y, cropRect.left, cropRect.bottom, slop)) return Handle.BOTTOM_LEFT
        if (isNear(x, y, cropRect.right, cropRect.bottom, slop)) return Handle.BOTTOM_RIGHT

        // Edges
        if (Math.abs(y - cropRect.top) < slop && x in cropRect.left..cropRect.right)
            return Handle.TOP_EDGE
        if (Math.abs(y - cropRect.bottom) < slop && x in cropRect.left..cropRect.right)
            return Handle.BOTTOM_EDGE
        if (Math.abs(x - cropRect.left) < slop && y in cropRect.top..cropRect.bottom)
            return Handle.LEFT_EDGE
        if (Math.abs(x - cropRect.right) < slop && y in cropRect.top..cropRect.bottom)
            return Handle.RIGHT_EDGE

        // Center (inside crop rect)
        if (cropRect.contains(x, y)) return Handle.CENTER

        return Handle.NONE
    }

    private fun isNear(x: Float, y: Float, targetX: Float, targetY: Float, slop: Float): Boolean {
        return Math.abs(x - targetX) < slop && Math.abs(y - targetY) < slop
    }
}
