package org.tensorflow.lite.examples.detect.camera

import org.tensorflow.lite.examples.detect.community.PostTab

data class VerityData(
    var verifyId: String = "",
    val resultBreed: Boolean = false,
    val resultMuzzle: Boolean = false,
    val resultSafety: Boolean = false,
    val uid: String = "",
    val nickname: String = "",
)
