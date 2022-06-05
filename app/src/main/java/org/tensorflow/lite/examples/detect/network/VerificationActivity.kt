package org.tensorflow.lite.examples.detect.network

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import kotlinx.android.synthetic.main.activity_verification.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.profile.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class VerificationActivity : AppCompatActivity() {
    private val api = RetrofitClient.create(FlaskApi::class.java)
    private var resultStringBreed = ""
    private var resultStringMuzzle = ""
    private var resultStringSafety = ""
    private var networkCheck = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        val testTxt = findViewById<TextView>(R.id.test)
        val btnVerify = findViewById<FloatingActionButton>(R.id.fab)
        val btnBreed = findViewById<Button>(R.id.breedGetBtn)
        val btnMuzzle = findViewById<Button>(R.id.muzzleGetBtn)
        val btnSafety = findViewById<Button>(R.id.safetyGetBtn)

        api.getTest().enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                testTxt.text = "Success"
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                testTxt.text = "Fail"
            }

        })

        btnVerify.setOnClickListener {
            getImage()
        }

        btnBreed.setOnClickListener {
            Log.e("resultStringBreed2", resultStringBreed)
            getImageResult(resultStringBreed)
        }

        btnMuzzle.setOnClickListener {
            Log.e("resultStringMuzzle2", resultStringMuzzle)
            getImageResult(resultStringMuzzle)
        }

        btnSafety.setOnClickListener {
            Log.e("resultStringSafety2", resultStringSafety)
            getImageResult(resultStringSafety)
        }

        // 하단 바
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.4f))
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.4f))
            .build()

    }

    private fun sendFile(userCd : String, image : MultipartBody.Part) {
        val service = RetrofitClient.create(FlaskApi::class.java) //레트로핏 통신 설정
        val call = service.postFile(userCd, image)!! //통신 API 패스 설정
        val dialog = LoadingDialog(this)

        // 다이얼로그 보여주기
        dialog.show()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        call.enqueue(object : Callback<FlaskDto> {
            override fun onResponse(call: Call<FlaskDto>, response: Response<FlaskDto>) {
                if (response.isSuccessful) {
                    Log.d("로그 ", "" + response?.body().toString())
                    Toast.makeText(applicationContext, "통신 성공", Toast.LENGTH_SHORT).show()

                    val resultBreed = response.body()?.result_breed
                    val resultBreedImgPath = response.body()?.result_breed_imgPath
                    val resultMuzzle = response.body()?.result_muzzle
                    val resultMuzzleImgPath = response.body()?.result_muzzle_imgPath
                    val resultSafety = response.body()?.result_safety
                    val resultSafetyImgPath = response.body()?.result_safety_imgPath

                    Log.e("resultBreedImgPath", resultBreedImgPath.toString())
                    Log.e("resultMuzzleImgPath", resultMuzzleImgPath.toString())
                    Log.e("resultSafetyImgPath", resultSafetyImgPath.toString())

                    if (resultBreedImgPath != null) {
                        resultStringBreed = resultBreedImgPath
                    }
                    if (resultMuzzleImgPath != null) {
                        resultStringMuzzle = resultMuzzleImgPath
                    }
                    if (resultSafetyImgPath != null) {
                        resultStringSafety = resultSafetyImgPath
                    }

                    val result1 = findViewById<TextView>(R.id.resultBreed)
                    // val result2 = findViewById<TextView>(R.id.result2)
                    val result3 = findViewById<TextView>(R.id.resultMuzzle)
                    // val result4 = findViewById<TextView>(R.id.result4)
                    val result5 = findViewById<TextView>(R.id.resultSafety)
                    // val result6 = findViewById<TextView>(R.id.result6)

                    result1.text = resultBreed.toString()
                    // result2.text = resultBreedImgPath
                    result3.text = resultMuzzle.toString()
                    // result4.text = resultMuzzleImgPath
                    result5.text = resultSafety.toString()
                    // result6.text = resultSafetyImgPath

                    // 다이얼로그 지우기
                    dialog.dismiss()


                } else {
                    Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()

                    // 다이얼로그 지우기
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<FlaskDto>, t: Throwable) {
                Log.d("로그 ", t.message.toString())

                // 다이얼로그 지우기
                dialog.dismiss()
            }
        })
    }

    private fun getImageResult(resultString: String) {

        api.getImage(resultString)
            .enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val toString = response.body()?.byteStream()

                val decodeStream = BitmapFactory.decodeStream(toString)
                val image = findViewById<ImageView>(R.id.imageView2)
                image.setImageBitmap(decodeStream)

                networkCheck = "true"
//                if (response.isSuccessful) {
//                    Toast.makeText(applicationContext, "통신 성공", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
//                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("로그 fail", t.message.toString())
                networkCheck = ""
            }
        })
    }


    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val imagePath = result.data!!.data

            val file = File(absolutelyPath(imagePath, this))
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

            Log.d("TAG",file.name)

            sendFile("file",body)
        }
    }
    fun getImage(){
        Log.d("mediaFile","사진변경 호출")
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/* video/*"
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE,"사용할 앱을 선택해주세요.")
        launcher.launch(chooserIntent)
    }

    // 절대경로 변환
    fun absolutelyPath(path: Uri?, context : Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    override fun onStart() {
        // 애니메이션 작동
        super.onStart()
        Handler().postDelayed({
            fab.show()
        }, 450)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> { // 홈으로 돌아가기
                fab.hide(AnimationFab.addVisibilityChanged)
                Handler().postDelayed({
                    finish()
                }, 300)
            }
        }
        return true
    }



}