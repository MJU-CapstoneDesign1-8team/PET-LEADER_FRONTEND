package org.tensorflow.lite.examples.detect.profile

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.content_verification_detail_result.*
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.camera.VerityData
import org.tensorflow.lite.examples.detect.network.ReportDialog
import org.tensorflow.lite.examples.detect.profile.adapter.ProfileDetectRVAdapter
import java.io.File

class ProfileResultActivity : AppCompatActivity() {
    private lateinit var profileResultRVAdapter: ProfileDetectRVAdapter
    private val verityDataList = mutableListOf<VerityData>()

    var myUid : String? = null
    val auth = Firebase.auth
    private val database = Firebase.database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_result)

        val rv = findViewById<RecyclerView>(R.id.rv_result_verify)

//        myUid = auth.currentUser?.uid
        myUid = "HozgblgIB1ZAoIAoOdimrm009zj2"
        if (myUid == null) {
            Toast.makeText(baseContext, "계정을 확인하지 못했습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // recyclerview 연결
        profileResultRVAdapter = ProfileDetectRVAdapter(verityDataList)
        rv.adapter = profileResultRVAdapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 데이터 가져오기
        getVerityData()

        // 클릭시 이벤트
        profileResultRVAdapter.setItemClickListener(object : ProfileDetectRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                Log.e("test click", "test click")
            }
        })
    }

    fun getVerityData() {
        Log.d("myUid", myUid!!)

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                snapshot.children.forEach {
                    Log.d("getVerityData dataModel", it.toString())
                    val item : VerityData? = it.getValue(VerityData::class.java)
                    Log.d("getVerityData item ", item.toString())
                    if (item == null) {
                        return@forEach
                    }
                    verityDataList.add(item!!)
                }

                verityDataList.reverse()
                profileResultRVAdapter.notifyDataSetChanged()
                Log.d("getVerityData",verityDataList.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("getVerityData error", "getVerityData error")
            }

        }
        //key 값만 가져옴
        myUid?.let { database.getReference("verify").orderByChild("uid").equalTo(myUid).addValueEventListener(postListener) }

    }



}