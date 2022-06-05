package org.tensorflow.lite.examples.detect.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R

class BoardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_detail)


        val titleView = findViewById<TextView>(R.id.titleArea)
        val contentView = findViewById<TextView>(R.id.contentArea)
        val timeView = findViewById<TextView>(R.id.timeArea)

        var post: PostData? = null

        val postId: String? = intent.getStringExtra("post_id")
        val postTab: String? = intent.getStringExtra("post_tab")
        Log.d("Detaila", "${postId}")

        val database = Firebase.database
        val postDB = database.getReference(postTab!!)

        //게시글 내용 불러오기
        postDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postModel in snapshot.children) {
//                    Log.d("Detail postmodel", "${postModel.key == postId}")
                    if (postModel.key == postId) {
                        post = postModel.getValue(PostData::class.java)

                        if (post == null) {
                            Toast.makeText(baseContext, "게시글을 불어오는 데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        titleView.text = post?.title
                        contentView.text = post?.content
                        timeView.text = post?.time
                        break
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}