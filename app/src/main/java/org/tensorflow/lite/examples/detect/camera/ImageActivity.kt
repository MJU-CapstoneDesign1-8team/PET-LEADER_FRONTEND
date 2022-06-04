package org.tensorflow.lite.examples.detect.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView


import org.tensorflow.lite.examples.detect.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImageActivity : AppCompatActivity() {

    private lateinit var cameraBtn : Button
    private lateinit var imageView : ImageView
    private var ourRequestCode : Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        cameraBtn = findViewById(R.id.captureBtn)
        imageView = findViewById(R.id.ImageView)

        cameraBtn.setOnClickListener{
            val cameraInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraInt, ourRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == ourRequestCode && resultCode == Activity.RESULT_OK){

            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
        }
    }

}