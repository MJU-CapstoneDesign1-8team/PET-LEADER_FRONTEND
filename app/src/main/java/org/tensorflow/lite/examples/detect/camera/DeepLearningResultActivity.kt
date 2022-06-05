package org.tensorflow.lite.examples.detect.camera

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import okhttp3.ResponseBody
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.network.FlaskApi
import org.tensorflow.lite.examples.detect.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeepLearningResultActivity : AppCompatActivity() {
    private val api = RetrofitClient.create(FlaskApi::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_breed_result)

        val resultStringBreed: String? = intent.getStringExtra("resultStringBreedUri")
        val img = findViewById<ImageView>(R.id.resultBreedImg)

        if (resultStringBreed != null) {
            api.getImage(resultStringBreed)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        val toString = response.body()?.byteStream()

                        val decodeStream = BitmapFactory.decodeStream(toString)
                        img.setImageBitmap(decodeStream)

                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("로그 fail", t.message.toString())
                    }
                })
        }
    }
}