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
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab
import org.tensorflow.lite.examples.detect.login.LoginActivity
import java.util.*


class ProfileActivity : AppCompatActivity() {
    private val postList = mutableListOf<PostData>()
    private val context: Context = this
    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        val database = Firebase.database
        val nickInfo = database.getReference("Nickname")
        val profileInfo = database.getReference("Profile")
        val currentUser = Firebase.auth.currentUser
        val storageReference = Firebase.storage.reference
        val storage = FirebaseStorage.getInstance()
        val mountainRef = storage.reference.child("profileImage")

        auth = Firebase.auth

        val userUID = auth.currentUser?.uid
        var userNick = "unknown"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nickInfo.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var nickData: Map<String, String> = snapshot.value as Map<String, String>
                userNick = when (nickData[userUID]) {
                    null -> "???"
                    else -> nickData[userUID]!!
                }
                userNickname.text = userNick
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        profileInfo.child(auth.currentUser?.uid.toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(dataModel in snapshot.children){
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        val profileSettingBtn = findViewById<ImageView>(R.id.profileSettingBtn)
        val logoutBtn = findViewById<TextView>(R.id.profileLogoutBtn)
        val userNickname = findViewById<TextView>(R.id.userNickname)
        val ProfileImage = findViewById<CircleImageView>(R.id.profile_logo)
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

        Glide.with(this).load(storageReference).into(ProfileImage)

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
                    profileAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        // recyclerview 연결
        val profileView = findViewById<RecyclerView>(R.id.profile_list)
        profileAdapter = ProfileAdapter(postList)
        profileView.adapter = profileAdapter
        profileView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 게시글 클릭 시 상세 게시글 페이지 전환
        profileAdapter.setItemClickListener(object : ProfileAdapter.OnItemClickListener {
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