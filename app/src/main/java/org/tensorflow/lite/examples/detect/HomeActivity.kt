package org.tensorflow.lite.examples.detect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.tensorflow.lite.examples.detect.faq.FaqActivity
import org.tensorflow.lite.examples.detect.yolov5.DetectorActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val faqBtn = findViewById<Button>(R.id.faqBtn)
        val camBtn = findViewById<Button>(R.id.camBtn)

        faqBtn.setOnClickListener {
            val faqIntent = Intent(this, FaqActivity::class.java)
            startActivity(faqIntent)
        }

        camBtn.setOnClickListener {
            val camIntent = Intent(this, DetectorActivity::class.java)
            startActivity(camIntent)
        }
    }
}