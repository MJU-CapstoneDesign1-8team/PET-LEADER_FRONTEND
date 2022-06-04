package org.tensorflow.lite.examples.detect

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import org.tensorflow.lite.examples.detect.faq.FaqActivity
import org.tensorflow.lite.examples.detect.profile.ProfileActivity
import org.tensorflow.lite.examples.detect.yolov5.DetectorActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val camBtn = findViewById<ImageView>(R.id.home_detect_button)
//        val faqBtn = findViewById<ImageView>(R.id.home_faq_button)
//        val profileBtn = findViewById<ImageView>(R.id.home_profile_button)

//        faqBtn.setOnClickListener {
//            val faqIntent = Intent(this, FaqActivity::class.java)
//            Log.d("click", "faq")
//            startActivity(faqIntent)
//        }

        camBtn.setOnClickListener {
            val camIntent = Intent(this, DetectorActivity::class.java)
            Log.d("click", "detect")
            startActivity(camIntent)
        }

//        profileBtn.setOnClickListener {
//            val profileIntent = Intent(this, ProfileActivity::class.java)
//            startActivity(profileIntent)
//            Log.d("click", "profile")
//        }
    }
}