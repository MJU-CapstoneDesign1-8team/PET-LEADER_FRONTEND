package org.tensorflow.lite.examples.detect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import org.tensorflow.lite.examples.detect.camera.ImageActivity
import org.tensorflow.lite.examples.detect.camera.VideoActivity
import org.tensorflow.lite.examples.detect.yolov5.DetectorActivity

class DetectChoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detect_choice)

        val videoBtn = findViewById<Button>(R.id.videoDetect)
        val imageBtn = findViewById<Button>(R.id.imageDetect)
        val realtimeBtn = findViewById<Button>(R.id.realtimeDetect)

        videoBtn.setOnClickListener{
            val videoIntent = Intent(this, VideoActivity::class.java)
            startActivity(videoIntent)
        }
        imageBtn.setOnClickListener{
            val imageIntent = Intent(this, ImageActivity::class.java)
            startActivity(imageIntent)
        }
        realtimeBtn.setOnClickListener{
            val realtimeIntent = Intent(this, DetectorActivity::class.java)
            startActivity(realtimeIntent)
        }

    }
}