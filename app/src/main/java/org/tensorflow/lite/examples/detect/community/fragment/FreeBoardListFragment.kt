package org.tensorflow.lite.examples.detect.community.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab
import org.tensorflow.lite.examples.detect.community.adapter.FreeBoardRVAdapter
import org.tensorflow.lite.examples.detect.databinding.FragmentFreeBoardListBinding


class FreeBoardListFragment : Fragment() {
    private lateinit var binding: FragmentFreeBoardListBinding
    private lateinit var freeBoardRVAdapter: FreeBoardRVAdapter
    private val communityDataList = mutableListOf<PostData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_free_board_list, container, false)

        val database = Firebase.database
        val postDB = database.getReference(PostTab.FREE.name)

        //게시글 목록 불러오기
        postDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postModel in snapshot.children) {
                    val post = postModel.getValue(PostData::class.java)
                    if (communityDataList.contains(post)) {
                        continue
                    }
                    communityDataList.add(post!!)
//                    Log.d("Post", post.toString())
                }
                freeBoardRVAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // recyclerview 연결
        freeBoardRVAdapter = FreeBoardRVAdapter(communityDataList)
        binding.rvCommunityListFree.adapter = freeBoardRVAdapter
        binding.rvCommunityListFree.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 게시글 클릭 시 상세 게시글 페이지 전환
        freeBoardRVAdapter.setItemClickListener(object : FreeBoardRVAdapter.OnItemClickListener {
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