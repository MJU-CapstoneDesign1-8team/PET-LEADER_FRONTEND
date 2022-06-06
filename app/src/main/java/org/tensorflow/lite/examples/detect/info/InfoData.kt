package org.tensorflow.lite.examples.detect.info

import java.io.Serializable

data class InfoData(
    val title: String = "",
    val simpleInfo: String = "",
    val specificInfo: String = "",
) : Serializable
