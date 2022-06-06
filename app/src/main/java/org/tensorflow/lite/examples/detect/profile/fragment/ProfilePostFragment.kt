package org.tensorflow.lite.examples.detect.profile.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab
import org.tensorflow.lite.examples.detect.databinding.FragmentProfilePostBinding
import org.tensorflow.lite.examples.detect.profile.adapter.ProfilePostAdapter

class ProfilePostFragment : Fragment() {
    private lateinit var binding: FragmentProfilePostBinding
    private val postList = mutableListOf<PostData>()
    private lateinit var profilePostAdapter: ProfilePostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_post, container, false)

        val database = Firebase.database
        val userInfo = FirebaseDatabase.getInstance().reference
        val currentUser = Firebase.auth.currentUser

        val auth = Firebase.auth
        val myUid = auth.currentUser?.uid
        Log.d("Profile Post", "$myUid")

        val tabList = listOf<String>(PostTab.FREE.name, PostTab.CARE.name, PostTab.WALK.name, PostTab.SHOW.name)
        val dbList = tabList.map { tab ->
            database.getReference(tab).orderByChild("uid").equalTo(myUid)
        }
        dbList.forEach {
            it.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postModel in snapshot.children) {
                        val post: PostData? = postModel.getValue(PostData::class.java)
                        Log.d("Profile Post", "$post")
                        if (post != null) {
                            postList.add(post)
                        }
                    }
                    profilePostAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

        // recyclerview 연결
        profilePostAdapter = ProfilePostAdapter(postList)
        binding.profileList.adapter = profilePostAdapter
        binding.profileList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 게시글 클릭 시 상세 게시글 페이지 전환
        profilePostAdapter.setItemClickListener(object : ProfilePostAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val postId: TextView = v.findViewById(R.id.tv_free_list_item_post_id)

                val intent = Intent(activity, BoardDetailActivity::class.java)
                intent.putExtra("post_id", postId.text as String)
                intent.putExtra("post_tab", PostTab.FREE.name)
//                Log.d("Intent", postId.text as String)
                startActivity(intent)
            }
        })

        return binding.root
    }
}