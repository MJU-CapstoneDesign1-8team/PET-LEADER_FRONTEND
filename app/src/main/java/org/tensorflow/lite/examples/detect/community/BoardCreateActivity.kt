package org.tensorflow.lite.examples.detect.community

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.profile.ProfileActivity
import java.text.SimpleDateFormat
import java.util.*

class BoardCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_create)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        val auth = Firebase.auth
        val userUID = auth.currentUser?.uid
        var userNick = "unknown"

        val database = Firebase.database
        val nickDB = database.getReference("Nickname")

        val postTitle = findViewById<EditText>(R.id.boardEditTitle)
        val postContent = findViewById<EditText>(R.id.boardEditContents)
        val submitBtn = findViewById<FloatingActionButton>(R.id.fab)


        // 하단 바
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.4f))
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.4f))
            .build()

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


        val spinner: Spinner = findViewById(R.id.select_tap_spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.tab_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        //게시글 작성 버튼
        submitBtn.setOnClickListener {
            val postTab = PostTab.getTab(spinner.selectedItem.toString())
            val titleName = postTitle.text.toString()
            val contentName = postContent.text.toString()
            val postDate = SimpleDateFormat("yyyy-MM-dd\nhh:mm:ss").format(Date()).toString()

            val postDB = database.getReference(postTab.name)

            val postModel = userUID?.let { it1 ->
                PostData(
                    tab = postTab,
                    title = titleName,
                    content = contentName,
                    uid = it1,
                    time = postDate,
                    nickname = userNick)
            }

            val postRef = postDB.push()
            postModel?.postId = postRef.key.toString() //랜덤 생성된 postId를 postModel에 저장
            postRef.setValue(postModel)

            Log.d("post_id", "${postModel?.postId}")
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { // 홈으로 돌아가기
                fab.hide(AnimationFab.addVisibilityChanged)
                Handler().postDelayed({
                    finish()
                }, 300)
            }
        }
        return true
    }
}