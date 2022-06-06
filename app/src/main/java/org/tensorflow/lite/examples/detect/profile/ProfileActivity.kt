package org.tensorflow.lite.examples.detect.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab
import org.tensorflow.lite.examples.detect.login.LoginActivity
import org.tensorflow.lite.examples.detect.profile.adapter.ProfileFeatureAdapter
import org.tensorflow.lite.examples.detect.profile.adapter.ProfilePostAdapter


class ProfileActivity : AppCompatActivity() {
    private val postList = mutableListOf<PostData>()
    private val context: Context = this
    private lateinit var profilePostAdapter: ProfilePostAdapter

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val currentUser = Firebase.auth.currentUser

        val database = Firebase.database
        val nickInfo = database.getReference("Nickname")
        val profileInfo = database.getReference("Profile")
        val storageReference = Firebase.storage.reference
        val storage = FirebaseStorage.getInstance()
        val profileDataType = findViewById<TextView>(R.id.profile_data_type)
        val profileSettingBtn = findViewById<ImageView>(R.id.profileSettingBtn)
        val logoutBtn = findViewById<TextView>(R.id.profileLogoutBtn)
        val userNickname = findViewById<TextView>(R.id.userNickname)
        var nickname = "unknown"
        val auth = Firebase.auth
        val myUid = auth.currentUser?.uid
        Log.d("Profile Post", "$myUid")


        val userUID = auth.currentUser?.uid
        var userNick = "unknown"

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

        val viewPager2 = findViewById<ViewPager2>(R.id.profile_viewpage)
        viewPager2.adapter = ProfileFeatureAdapter(this)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL;

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    viewPager2.currentItem = position;
                    profileDataType.text = when(position) {
                        0 -> "내 글"
                        1 -> "검사 결과"
                        else -> "Undfined"
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }
}