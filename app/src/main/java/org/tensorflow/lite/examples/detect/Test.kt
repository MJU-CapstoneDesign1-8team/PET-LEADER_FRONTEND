package org.tensorflow.lite.examples.detect

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.community.PostTab

class Test {
    fun main(args: Array<String>) {
        val database = Firebase.database
        val postDB = database.getReference(PostTab.FREE.name)
        postDB.ref.child("a").setValue("b")
    }
}