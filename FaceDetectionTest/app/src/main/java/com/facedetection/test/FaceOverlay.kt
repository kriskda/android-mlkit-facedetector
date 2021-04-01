package com.facedetection.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.facedetection.test.model.Faces

class FaceOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object{
        private const val POINT_RADIUS = 5f
    }

    private val paint = Paint().apply {
        strokeWidth = 3f
        color = Color.RED
        style = Paint.Style.STROKE
    }

    private var faces: Faces? = null

    fun addContours(faces: Faces) {
        this.faces = faces
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        faces?.facePointsList?.forEach { facePoints ->
            facePoints.faceShape.forEach { canvas.drawCircle(it.x, it.y, POINT_RADIUS, paint) }
            facePoints.noseShape.forEach { canvas.drawCircle(it.x, it.y, POINT_RADIUS, paint) }
            facePoints.cheeksShape.forEach { canvas.drawCircle(it.x, it.y, POINT_RADIUS, paint) }
            facePoints.eyebrowsShape.forEach { canvas.drawCircle(it.x, it.y, POINT_RADIUS, paint) }
            facePoints.eyesShape.forEach { canvas.drawCircle(it.x, it.y, POINT_RADIUS, paint) }
            facePoints.lipsPoints.forEach { canvas.drawCircle(it.x, it.y, POINT_RADIUS, paint) }
        }
    }
}