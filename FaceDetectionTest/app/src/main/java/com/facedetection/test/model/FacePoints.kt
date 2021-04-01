package com.facedetection.test.model

import android.graphics.PointF

data class FacePoints(
    val faceShape: List<PointF>,
    val noseShape: List<PointF>,
    val cheeksShape: List<PointF>,
    val eyebrowsShape: List<PointF>,
    val eyesShape: List<PointF>,
    val lipsPoints: List<PointF>
)
