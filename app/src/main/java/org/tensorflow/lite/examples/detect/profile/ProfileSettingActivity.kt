package org.tensorflow.lite.examples.detect.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import org.tensorflow.lite.examples.detect.R

class ProfileSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setting)

        val backBtn = findViewById<ImageButton>(R.id.backBtn)

        backBtn.setOnClickListener {
            val backIntent = Intent(this, ProfileActivity::class.java)
            startActivity(backIntent)
            finish()
        }

    }

}