package org.tensorflow.lite.examples.detect.community.fragment

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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab
import org.tensorflow.lite.examples.detect.community.adapter.WalkBoardRVAdapter
import org.tensorflow.lite.examples.detect.databinding.FragmentWalkBoardListBinding


class WalkBoardListFragment : Fragment() {
    private lateinit var binding : FragmentWalkBoardListBinding
    private lateinit var walkBoardRVAdapter: WalkBoardRVAdapter
    private val communityDataList = mutableListOf<PostData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_walk_board_list, container, false)
        val database = Firebase.database
        val postDB = database.getReference(PostTab.WALK.name)

        //게시글 목록 불러오기
        postDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postModel in snapshot.children) {
                    val post = postModel.getValue(PostData::class.java)
                    post?.postId = postModel.key!!
                    if (communityDataList.contains(post)) {
                        continue
                    }
                    communityDataList.add(post!!)
//                    Log.d("Post", post.toString())
                }
                walkBoardRVAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // recyclerview 연결
        walkBoardRVAdapter = WalkBoardRVAdapter(communityDataList)
        binding.rvCommunityListWalk.adapter = walkBoardRVAdapter
        binding.rvCommunityListWalk.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 클릭시 이벤트
        walkBoardRVAdapter.setItemClickListener(object : WalkBoardRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val postId: TextView = v.findViewById(R.id.tv_free_list_item_post_id)

                val intent = Intent(activity, BoardDetailActivity::class.java)
                intent.putExtra("post_id", postId.text as String)
                intent.putExtra("post_tab", PostTab.WALK.name)
                Log.d("Intent", postId.text as String)
                startActivity(intent)
            }

        })

        return binding.root
    }
}