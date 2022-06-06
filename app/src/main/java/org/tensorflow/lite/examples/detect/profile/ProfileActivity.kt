package org.tensorflow.lite.examples.detect.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab
import org.tensorflow.lite.examples.detect.login.LoginActivity


class ProfileActivity : AppCompatActivity() {
    private val postList = mutableListOf<PostData>()
    private val context: Context = this
    private lateinit var profilePostAdapter: ProfilePostAdapter

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        val database = Firebase.database
        val userInfo = FirebaseDatabase.getInstance().reference
        val currentUser = Firebase.auth.currentUser


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        val profileSettingBtn = findViewById<ImageView>(R.id.profileSettingBtn)
        val logoutBtn = findViewById<TextView>(R.id.profileLogoutBtn)
        val userNickname = findViewById<TextView>(R.id.userNickname)
        var nickname = "unknown"
        val auth = Firebase.auth
        val myUid = auth.currentUser?.uid
        Log.d("Profile Post", "$myUid")



        val freeDB = database.getReference(PostTab.FREE.name)
        val careDB = database.getReference(PostTab.CARE.name)
        val walkDB = database.getReference(PostTab.WALK.name)
        val showDB = database.getReference(PostTab.SHOW.name)

        val tabList = listOf<String>(PostTab.FREE.name, PostTab.CARE.name, PostTab.WALK.name, PostTab.SHOW.name)
        
      
        //프로필 세팅 화면으로 감
        profileSettingBtn.setOnClickListener{

            val settingIntent = Intent(this, ProfileSettingActivity::class.java )
            startActivity(settingIntent)
        }

        //로그아웃
        logoutBtn.setOnClickListener{
            if(currentUser != null) {
                auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(baseContext, "로그아웃 성공", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(baseContext, "로그인을 해주세요", Toast.LENGTH_LONG).show()
            }
        }
        val dbList = tabList.map { tab ->
            database.getReference(tab).orderByChild("uid").equalTo(myUid)
        }
        dbList.forEach {
            it.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postModel in snapshot.children) {
                        val post: PostData? = postModel.getValue(PostData::class.java)
                        Log.d("Profile Post", "$post")
                        if (post != null) {
                            postList.add(post)
                        }
                    }
                    profilePostAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        // recyclerview 연결
        val profileView = findViewById<RecyclerView>(R.id.profile_list)
        profilePostAdapter = ProfilePostAdapter(postList)
        profileView.adapter = profilePostAdapter
        profileView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 게시글 클릭 시 상세 게시글 페이지 전환
        profilePostAdapter.setItemClickListener(object : ProfilePostAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val postId: TextView = v.findViewById(R.id.tv_free_list_item_post_id)

                val intent = Intent(context, BoardDetailActivity::class.java)
                intent.putExtra("post_id", postId.text as String)
                intent.putExtra("post_tab", PostTab.FREE.name)
//                Log.d("Intent", postId.text as String)
                startActivity(intent)
            }
        })


    }
}