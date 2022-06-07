package org.tensorflow.lite.examples.detect.comment

import com.google.firebase.database.Exclude

data class CommentModel(
    val commentTitle: String = "",
    val commentCreatedTime: String = "",
    val commentNickname: String = "",
    val commentUid : String = "",
    val commentThumbInt : Int = 0
){
    @Exclude
    fun toMap() : Map<String, Any> {
        return mapOf(
            "commentTitle" to commentTitle,
            "commentCreatedTime" to commentCreatedTime,
            "commentNickname" to commentNickname,
            "commentUid" to commentUid,
            "commentThumbInt" to commentThumbInt
        )
    }
}