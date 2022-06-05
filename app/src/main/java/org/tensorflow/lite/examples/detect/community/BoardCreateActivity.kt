package org.tensorflow.lite.examples.detect.community

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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
        val userUID = auth.currentUser!!.uid
        var userNick = "unknown"

        val database = Firebase.database
        val postDB = database.getReference("posts")
        val nickDB = database.getReference("Nickname")

        val postTitle = findViewById<EditText>(R.id.boardEditTitle)
        val postContent = findViewById<EditText>(R.id.boardEditContents)
        val submitBtn = findViewById<FloatingActionButton>(R.id.fab)


        nickDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //유저 닉네임 데이터 수신
                var nickData: Map<String, String> = snapshot.value as Map<String, String>
                userNick = when (nickData[userUID]) {
                    null -> "???"
                    else -> nickData[userUID]!!
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        postDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (post in snapshot.children) {
                    Log.d("post database", post.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        submitBtn.setOnClickListener {
            val titleName = postTitle.text.toString()
            val contentName = postContent.text.toString()
            val postDate = SimpleDateFormat("yyyy-MM-dd, hh:mm:ss").format(Date()).toString()

            val postModel = PostData(
                title = titleName,
                content = contentName,
                uid = userUID,
                time = postDate,
                nickname = userNick)
            postDB.push().setValue(postModel)
        }
    }
}