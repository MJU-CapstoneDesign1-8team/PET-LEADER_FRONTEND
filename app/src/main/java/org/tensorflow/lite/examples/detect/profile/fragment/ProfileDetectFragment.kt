package org.tensorflow.lite.examples.detect.profile.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.camera.VerityData
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostTab
import org.tensorflow.lite.examples.detect.databinding.FragmentProfileDetectBinding
import org.tensorflow.lite.examples.detect.profile.DetailResultActivity
import org.tensorflow.lite.examples.detect.profile.adapter.ProfileDetectRVAdapter

class ProfileDetectFragment : Fragment() {
    private lateinit var binding: FragmentProfileDetectBinding
    private val verityDataList = mutableListOf<VerityData>()
    private val verityKeyList = mutableListOf<String>()
    private lateinit var profileResultRVAdapter: ProfileDetectRVAdapter

    var myUid : String? = null
    val auth = Firebase.auth
    private val database = Firebase.database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_detect, container, false)

        val rv = binding.rvResultVerify

        myUid = auth.currentUser?.uid
        if (myUid == null) {
            Toast.makeText(context, "계정을 확인하지 못했습니다.", Toast.LENGTH_SHORT).show()
        }

        // recyclerview 연결
        profileResultRVAdapter = ProfileDetectRVAdapter(verityDataList)
        rv.adapter = profileResultRVAdapter
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 데이터 가져오기
        getVerityData()

        // 클릭시 이벤트
        profileResultRVAdapter.setItemClickListener(object : ProfileDetectRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                Log.e("test click", "test click")

                val intent = Intent(activity, DetailResultActivity::class.java)
                intent.putExtra("key", verityKeyList[position])
                startActivity(intent)
            }

        })

        return binding.root
    }


    fun getVerityData() {
        Log.d("myUid", myUid!!)

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                verityDataList.clear()

                snapshot.children.forEach {
//                    Log.d("getVerityData dataModel", it.toString())
                    val item : VerityData? = it.getValue(VerityData::class.java)
//                    Log.d("getVerityData item ", item.toString())
                    if (item == null) {
                        return@forEach
                    }
                    verityDataList.add(item)
                    verityKeyList.add(it.key.toString())
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