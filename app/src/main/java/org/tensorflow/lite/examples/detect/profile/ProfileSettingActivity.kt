package org.tensorflow.lite.examples.detect.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_setting.*
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.camera.VerityData
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ProfileSettingActivity : AppCompatActivity() {

    private lateinit var storage: FirebaseStorage
    private lateinit var firestore: FirebaseFirestore
    private lateinit var database : DatabaseReference
    private lateinit var auth: FirebaseAuth
    val IMAGE_PICK = 111
    private lateinit var uri : Uri
    private lateinit var key : String
    //private var fileName : String? =  ".jpg"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)

        storage = FirebaseStorage.getInstance()
        firestore=FirebaseFirestore.getInstance()

        val auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        val userUID = auth.currentUser?.uid
        var userNick1 = "unknown"

        val database = Firebase.database
        val nickDB = database.getReference("Nickname")


        val changeBtn = findViewById<TextView>(R.id.changeSettingBtn)
        val imageAdd = findViewById<ImageButton>(R.id.addProfileBtn)
        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        val changeNick = findViewById<EditText>(R.id.SettingNickname)

        //hint ????????? ????????? ????????????
        nickDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var nickData: Map<String, String> = snapshot.value as Map<String, String>
                userNick1 = when (nickData[userUID]) {
                    null -> "???"
                    else -> nickData[userUID]!!
                }
                changeNick.hint = userNick1
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        //???????????? ??????
        backBtn.setOnClickListener {
            val backIntent = Intent(this, ProfileActivity::class.java)
            startActivity(backIntent)
            finish()
        }

        //???????????? ?????? ????????? ????????????
        changeBtn.setOnClickListener{

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()

        }

        //??????????????? ??????
        imageAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
            startActivityForResult(intent, IMAGE_PICK)
        }

    }
}