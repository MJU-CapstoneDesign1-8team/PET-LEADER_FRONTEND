package org.tensorflow.lite.examples.detect.camera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import org.tensorflow.lite.examples.detect.R

class VideoActivity : AppCompatActivity() {

    private lateinit var videoView : VideoView
    private var ourRequestCode : Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.videoView)

        val mediaCollection = MediaController(this)
        mediaCollection.setAnchorView(videoView)
        videoView.setMediaController(mediaCollection)
    }

    fun startVideo(view: View) {
        //start intent to capture video
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, ourRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ourRequestCode && resultCode == RESULT_OK){
            //get data from uri
            val videoUri = data?.data
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
    }
}