package org.tensorflow.lite.examples.detect.camera

import org.tensorflow.lite.examples.detect.community.PostTab

data class VerityData(

    var verifyId: String = "",
    val resultBreed: Boolean,
    val resultMuzzle: Boolean,
    val resultSafety: Boolean,
    val uid: String = "",
    val nickname: String = ""

)
