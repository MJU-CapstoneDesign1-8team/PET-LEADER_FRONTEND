package org.tensorflow.lite.examples.detect.community

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.comment.CommentLVAdapter
import org.tensorflow.lite.examples.detect.comment.CommentModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BoardDetailActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    val current = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ISO_DATE
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = current.format(formatter)

    private lateinit var commentAdapter: CommentLVAdapter
    private val commentDataList = mutableListOf<CommentModel>()
    private val database = Firebase.database

    lateinit var userNick : String
    var userUID : String? = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_detail)

        val auth = Firebase.auth
        userUID = auth.currentUser?.uid
        userNick = "unknown"
        val nickDB = database.getReference("Nickname")


        val titleView = findViewById<TextView>(R.id.titleArea)
        val contentView = findViewById<TextView>(R.id.contentArea)
        val timeView = findViewById<TextView>(R.id.timeArea)
        val backBtnLinear = findViewById<LinearLayout>(R.id.backBtnLinear)
        var post: PostData? = null

        val postId: String? = intent.getStringExtra("post_id")
        val postTab: String? = intent.getStringExtra("post_tab")
        Log.d("Detail post Id", "${postId}")

        val database = Firebase.database
        val postDB = database.getReference(postTab!!)
        backBtnLinear.setOnClickListener {
            finish()
        }
        //게시글 내용 불러오기
        postDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postModel in snapshot.children) {
                    Log.d("Detail postmodel", "${postModel.key == postId}")
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
            }
        })


        // 닉네임 정보 가져오기
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


        // listview 연결
        val commentLV = findViewById<ListView>(R.id.commnetLV)
        commentAdapter = CommentLVAdapter(commentDataList)
        commentLV.adapter = commentAdapter

        // 댓글 버튼
        val commentBtn = findViewById<ImageView>(R.id.commentBtn)
        commentBtn.setOnClickListener {
            if (postId != null) {
                insertComment(postId)
            }
        }
        // 댓글 가져오기
        if (postId != null) {
            getCommentData(postId)
        }


    }

    // 댓글 DB에 넣기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertComment(key : String){
        val commentArea = findViewById<EditText>(R.id.commentArea)
        if(commentArea.text.toString() !=""){
            database.getReference("comment_list")
                .child(key)
                .push()
                .setValue(
                    userUID?.let {
                        CommentModel(
                            commentArea.text.toString(),
                            formatted,
                            userNick, // 닉네임 불러오기
                            it
                        )
                    }
                )
            commentArea.setText("")
        } else{
            Toast.makeText(this,"내용을 입력하지 않았습니다",Toast.LENGTH_LONG).show()
        }

    }

    // 댓글 가져오기
    private fun getCommentData(key : String){
        val postListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                commentDataList.clear()

                for(dataModel in snapshot.children){
                    Log.d("asvvv",dataModel.toString())

                    val item = dataModel.getValue(CommentModel::class.java)
                    commentDataList.add(item!!)


                    if(userUID == item.commentUid) {
                        Log.d("asvvv", item.commentNickname)
                    }
                }

                commentDataList.reverse()
                commentAdapter.notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        //key 값만 가져옴
        database.getReference("comment_list").child(key).addValueEventListener(postListener)

    }
}