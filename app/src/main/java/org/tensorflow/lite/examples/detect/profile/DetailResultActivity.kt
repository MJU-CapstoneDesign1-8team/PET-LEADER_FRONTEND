package org.tensorflow.lite.examples.detect.profile

import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_faq.*
import kotlinx.android.synthetic.main.activity_faq.bottomAppBar
import kotlinx.android.synthetic.main.activity_faq.fab
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_faq.*
import kotlinx.android.synthetic.main.content_verification_detail_result.*
import kotlinx.android.synthetic.main.dialog_report.*
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.camera.VerityData
import org.tensorflow.lite.examples.detect.databinding.ActivityDetailResultBinding
import org.tensorflow.lite.examples.detect.network.ReportDialog
import org.w3c.dom.Text
import java.io.File

class DetailResultActivity : AppCompatActivity() {

    private lateinit var key : String
    var imageView: ImageView? = null

    var myUid : String? = null
    val auth = Firebase.auth

    var resultReport : Boolean = false
    private lateinit var dialogReport: ReportDialog

    private val database = Firebase.database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail_result)

        val checkBreedBtn = findViewById<Button>(R.id.breedGetBtn_result)
        val checkMuzzleBtn = findViewById<Button>(R.id.muzzleGetBtn_result)
        val checkSafetyBtn = findViewById<Button>(R.id.safetyGetBtn_result)
        imageView = findViewById(R.id.imageView2_result)
        val reportBtn = findViewById<FloatingActionButton>(R.id.fab)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        key = intent.getStringExtra("key").toString()
        Log.e("ketket", key)

        getVerityData(key)


        // 뒤로가기 (홈)
        val backBtn = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            backBtn.hide(AnimationFab.addVisibilityChanged)
            Handler().postDelayed({
                finish()
            }, 300)
        }

        // 하단 바
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.4f))
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.4f))
            .build()

        checkBreedBtn.setOnClickListener {
            Log.e("verityId key", key)
            loadImage(key, "breed")
        }

        checkMuzzleBtn.setOnClickListener {
            Log.e("verityId key", key)
            loadImage(key, "muzzle")
        }

        checkSafetyBtn.setOnClickListener {
            Log.e("verityId key", key)
            loadImage(key, "safety")
        }

        reportBtn.setOnClickListener {
            val dialogReport = ReportDialog(this)
            Log.e("resultReport", resultReport.toString())
            if (resultReport) {
                dialogReport.show()

                dialogReport.report_btn?.setOnClickListener {
                    Toast.makeText(applicationContext, "신고 접수", Toast.LENGTH_SHORT).show()
                    dialogReport.dismiss()
                }

                dialogReport.exit_btn?.setOnClickListener {
                    dialogReport.dismiss()
                }
            } else {
                Toast.makeText(baseContext, "신고 조건이 충족 되지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun getVerityData(key : String) {

        val resultBreedTv = findViewById<TextView>(R.id.resultBreed_result)
        val resultMuzzleTv = findViewById<TextView>(R.id.resultMuzzle_result)
        val resultSafetyTv = findViewById<TextView>(R.id.resultSafety_result)
        val reportresult = findViewById<TextView>(R.id.textView2222)
        val addressResult = findViewById<TextView>(R.id.address_result)
        Log.e("kkkk", key)
        val postListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val dataModel = snapshot.getValue(VerityData::class.java)
                Log.d("dataModel", dataModel.toString())

                if (dataModel != null) {
                    val resultBreed = dataModel.resultBreed
                    val resultMuzzle = dataModel.resultMuzzle
                    val resultSafety = dataModel.resultSafety
                    addressResult.text = dataModel.address

                    if (resultBreed == true) {
                        resultBreedTv.text = "맹견"
                    } else resultBreedTv.text = "미인식"

                    if (resultMuzzle == true) {
                        resultMuzzleTv.text = "착용"
                    } else resultMuzzleTv.text = "미착용"

                    if (resultSafety == true) {
                        resultSafetyTv.text = "착용"
                    } else resultSafetyTv.text = "미착용"


                    if (resultBreed == true && resultMuzzle == false && resultSafety == false) {
                        reportresult.text = "신고 가능"
                        resultReport = true
                    } else {
                        reportresult.text = "신고 반려"
                        reportresult.setBackgroundColor(Color.parseColor("#3F51B5"))
                        resultReport = false
                    }
//                    resultBreedTv.text = dataModel.resultBreed.toString()
//                    resultMuzzleTv.text = dataModel.resultMuzzle.toString()
//                    resultSafetyTv.text = dataModel.resultSafety.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("getVerityData error", "getVerityData error")
            }

        }
        //key 값만 가져옴
        //database.getReference("verify").orderByChild("verifyId").equalTo(key).addValueEventListener(postListener)
        database.getReference("verify").child(key).addValueEventListener(postListener)

    }

    fun loadImage(id: String, file: String) {
        Log.e("verityId", id)
        val storageRef = FirebaseStorage.getInstance().reference.child("image/${id}/${file}.jpg3e")
        val localFile = File.createTempFile("tempFile", "jpeg")

        storageRef.getFile(localFile).addOnSuccessListener {
//                Log.d("Storage", bitmap.toString())
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            imageView2_result.setImageBitmap(bitmap)
        }.addOnFailureListener {
            Log.d("Storage", storageRef.toString())
            Log.d("Storage", it.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> { // 홈으로 돌아가기
                fab.hide(AnimationFab.addVisibilityChanged)
                Handler().postDelayed({
                    finish()
                }, 300)
            }
        }
        return true
    }

    override fun onStart() {
        // 애니메이션 작동
        super.onStart()
        Handler().postDelayed({
            fab.show()
        }, 450)
    }
}