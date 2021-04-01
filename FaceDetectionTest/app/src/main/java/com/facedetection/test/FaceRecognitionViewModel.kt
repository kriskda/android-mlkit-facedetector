package com.facedetection.test

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.facedetection.test.model.Faces
import kotlinx.coroutines.launch

class FaceRecognitionViewModel : ViewModel() {

    private val faceRecognizer = FaceRecognizer()

    private var _content : MutableLiveData<Faces> = MutableLiveData()
    val content : LiveData<Faces> get() =_content

    fun recognize(bitmap: Bitmap) {
        viewModelScope.launch {
            _content.value = faceRecognizer.recognize(bitmap)
        }
    }
}