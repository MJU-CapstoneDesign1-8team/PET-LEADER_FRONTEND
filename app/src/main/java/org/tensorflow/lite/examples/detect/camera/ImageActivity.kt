package org.tensorflow.lite.examples.detect.camera


import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.detect.R


class ImageActivity : AppCompatActivity() {

    var imageView: ImageView? = null
    var imageUri: Uri? = null
    val camRequestId = 1222

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val cameraBtn = findViewById<Button>(R.id.captureBtn)
        imageView = findViewById(R.id.imageView2)

        cameraBtn.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val imagePath = createImage()
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath)
            startActivityForResult(intent, camRequestId)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == camRequestId) {
            if (resultCode == RESULT_OK) {
                imageView!!.setImageURI(imageUri)
            }
        }
    }

    private fun createImage(): Uri? {
        var uri: Uri? = null
        val resolver = contentResolver
        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            // DCIM
            // picture
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val imgName = System.currentTimeMillis().toString()
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "$imgName.jpg")
        contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + "My Images/")
        val finalUri = resolver.insert(uri, contentValues)
        imageUri = finalUri
        return finalUri
    }

}