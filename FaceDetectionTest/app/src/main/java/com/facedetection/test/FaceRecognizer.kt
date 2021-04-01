package com.facedetection.test

import android.graphics.Bitmap
import android.graphics.PointF
import android.util.Log
import com.facedetection.test.model.FacePoints
import com.facedetection.test.model.Faces
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine

class FaceRecognizer {

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()

    suspend fun recognize(bitmap: Bitmap): Faces =
        suspendCancellableCoroutine { continuation ->
            Log.i("MLtest", "test")
            val image = InputImage.fromBitmap(bitmap, 0)
            FaceDetection.getClient(options).process(image)
                .addOnSuccessListener {
                    val value = Faces(it.toFacePoints())
                    continuation.resume(value, null)
                }
                .addOnFailureListener { e -> Log.i("MLtest", e.toString()) }
        }

    private fun List<Face>.toFacePoints(): List<FacePoints> =
        map {
            val eyesPoints = it.getPoints(FaceContour.LEFT_EYE) +
                    it.getPoints(FaceContour.RIGHT_EYE)
            val cheekPoints = it.getPoints(FaceContour.LEFT_CHEEK) +
                    it.getPoints(FaceContour.RIGHT_CHEEK)
            val nosePoints = it.getPoints(FaceContour.NOSE_BOTTOM) +
                    it.getPoints(FaceContour.NOSE_BRIDGE)
            val eyebrowPoints = it.getPoints(FaceContour.LEFT_EYEBROW_TOP) +
                    it.getPoints(FaceContour.LEFT_EYEBROW_BOTTOM) +
                    it.getPoints(FaceContour.RIGHT_EYEBROW_TOP) +
                    it.getPoints(FaceContour.RIGHT_EYEBROW_BOTTOM)
            val facePoints = it.getPoints(FaceContour.FACE)
            val lipsPoints = it.getPoints(FaceContour.LOWER_LIP_BOTTOM) +
                    it.getPoints(FaceContour.LOWER_LIP_TOP) +
                    it.getPoints(FaceContour.UPPER_LIP_TOP) +
                    it.getPoints(FaceContour.UPPER_LIP_BOTTOM)

            FacePoints(
                facePoints,
                nosePoints,
                cheekPoints,
                eyebrowPoints,
                eyesPoints,
                lipsPoints
            )
        }

    private fun Face.getPoints(type: Int): List<PointF> =
        getContour(type)?.points ?: emptyList()
}