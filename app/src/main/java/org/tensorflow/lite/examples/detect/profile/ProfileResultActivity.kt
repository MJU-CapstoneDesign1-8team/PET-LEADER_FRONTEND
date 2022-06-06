package org.tensorflow.lite.examples.detect.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.camera.VerityData
import org.tensorflow.lite.examples.detect.community.PostData

class ProfileResultActivity : AppCompatActivity() {
    private lateinit var profileResultRVAdapter: ProfileResultRVAdapter
    private val verityDataList = mutableListOf<VerityData>()

    var myUid : String? = ""
    val auth = Firebase.auth
    private val database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_result)

        val rv = findViewById<RecyclerView>(R.id.rv_result_verify)

        myUid = auth.currentUser?.uid

        // recyclerview 연결
        profileResultRVAdapter = ProfileResultRVAdapter(verityDataList)
        rv.adapter = profileResultRVAdapter
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 데이터 가져오기
        getVerityData()

        // 클릭시 이벤트
        profileResultRVAdapter.setItemClickListener(object : ProfileResultRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
//                val postId: TextView = v.findViewById(R.id.tv_free_list_item_post_id)
//                val intent = Intent(activity, BoardDetailActivity::class.java)
//                intent.putExtra("post_id", postId.text as String)
//                intent.putExtra("post_tab", PostTab.CARE.name)
//                Log.d("Intent", postId.text as String)
//                startActivity(intent)
                Log.e("test click", "test click")
            }

        })
    }

    fun getVerityData() {
        Log.e("myUid", myUid!!)
        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(dataModel in snapshot.children){
                    Log.d("getVerityData dataModel",dataModel.toString())
                    val item = dataModel.getValue()
                    //val item = dataModel.getValue(VerityData::class.java)

                    //val item_uid = dataModel.getValue("uid")
                    Log.e("getVerityData item ", item.toString())
                    //Log.e("getVerityData item ", item_uid.toString())
                    //verityDataList.add(VerityData())
                }

//                verityDataList.reverse()
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