package org.tensorflow.lite.examples.detect.yolov5.modelbutton

import android.util.Log
import android.view.View
import org.tensorflow.lite.examples.detect.R

class ModelBtnManager {
    val btnList: MutableList<ModelBtnData> = mutableListOf(
        ModelBtnData("dog-breed416.tflite", R.drawable.detect_breed_button, "견종"),
        ModelBtnData("dog-leash.tflite", R.drawable.detect_leash_button, "목줄"),
        ModelBtnData("dog-muzzle.tflite", R.drawable.detect_muzzle_button, "입마개"),
    )
}