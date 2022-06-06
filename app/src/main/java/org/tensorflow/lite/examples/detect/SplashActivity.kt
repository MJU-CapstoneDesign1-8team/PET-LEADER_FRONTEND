package org.tensorflow.lite.examples.detect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.tensorflow.lite.examples.detect.community.CommunityActivity
import org.tensorflow.lite.examples.detect.onboard.OnBoardActivity
import org.tensorflow.lite.examples.detect.login.LoginActivity
import org.tensorflow.lite.examples.detect.profile.ProfileActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, OnBoardActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}