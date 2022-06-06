package org.tensorflow.lite.examples.detect.profile

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
import org.tensorflow.lite.examples.detect.HomeActivity
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        val database = Firebase.database
        val userInfo = FirebaseDatabase.getInstance().reference
        auth = Firebase.auth
        val currentUser = auth.currentUser


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileSettingBtn = findViewById<ImageView>(R.id.profileSettingBtn)
        val logoutBtn = findViewById<TextView>(R.id.profileLogoutBtn)
        val userNickname = findViewById<TextView>(R.id.userNickname)
        var nickname = "unknown"

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

    }
}