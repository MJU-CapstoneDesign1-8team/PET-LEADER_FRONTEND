package org.tensorflow.lite.examples.detect.community

data class PostData(
    var postId: String = "",
    val title: String = "",
    val content: String = "",
    val uid: String = "",
    val nickname: String = "",
    val time: String = "",
)
