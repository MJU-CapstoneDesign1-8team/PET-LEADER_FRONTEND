package org.tensorflow.lite.examples.detect

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import org.tensorflow.lite.examples.detect.community.CommunityActivity
import org.tensorflow.lite.examples.detect.faq.FaqActivity
import org.tensorflow.lite.examples.detect.profile.ProfileActivity
import org.tensorflow.lite.examples.detect.yolov5.DetectorActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val camBtn = findViewById<ImageView>(R.id.home_detect_button)
        val faqBtn = findViewById<ImageView>(R.id.home_faq_button)
        val infoBtn = findViewById<ImageView>(R.id.home_info_button)
        val commBtn = findViewById<ImageView>(R.id.home_community_button)

        faqBtn.setOnClickListener {
            val faqIntent = Intent(this, FaqActivity::class.java)
            Log.d("click", "faq")
            startActivity(faqIntent)
        }

        camBtn.setOnClickListener {
            val camIntent = Intent(this, DetectorActivity::class.java)
            Log.d("click", "detect")
            startActivity(camIntent)
        }

        infoBtn.setOnClickListener {
//            val infoIntent = Intent(this, ProfileActivity::class.java)
//            startActivity(profileIntent)
            Log.d("click", "info")
        }

        commBtn.setOnClickListener {
            val commIntent = Intent(this, CommunityActivity::class.java)
            Log.d("click", "community")
            startActivity(commIntent)
        }
    }
}