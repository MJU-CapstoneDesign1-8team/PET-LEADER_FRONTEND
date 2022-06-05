package org.tensorflow.lite.examples.detect.community

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import java.text.SimpleDateFormat
import java.util.*

class BoardCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_create)

        val auth = Firebase.auth
        val database = Firebase.database
        val myRef = database.getReference("posts")

        val postTitle = findViewById<EditText>(R.id.boardEditTitle)
        val postContent = findViewById<EditText>(R.id.boardEditContents)
        val submitBtn = findViewById<FloatingActionButton>(R.id.fab)


//
//        val sdf = SimpleDateFormat("hh")
//        Log.d("DATE", sdf.format(dt).toString().toString() + "시")

        submitBtn.setOnClickListener {
            val titleName = postTitle.text.toString()
            val contentName = postContent.text.toString()
            val dt = Date()
            Log.d("DATE", dt.toString())
            val dateFormat = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a")
            Log.d("DATE", dateFormat.format(dt).toString())

            val postModel = PostData(titleName, contentName, auth.currentUser!!.uid, dateFormat.format(dt).toString())
            myRef.push().setValue(postModel)
        }
    }
}