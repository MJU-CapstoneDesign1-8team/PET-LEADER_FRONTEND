package org.tensorflow.lite.examples.detect.community.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import org.tensorflow.lite.examples.detect.community.adapter.ShowBoardRVAdapter
import org.tensorflow.lite.examples.detect.databinding.FragmentShowBoardListBinding

class ShowBoardListFragment : Fragment() {
    private lateinit var binding : FragmentShowBoardListBinding
    private lateinit var showBoardRVAdapter: ShowBoardRVAdapter
    private val communityDataList = mutableListOf<PostData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_board_list, container, false)
        val database = Firebase.database
        val postDB = database.getReference(PostTab.SHOW.name)

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
                showBoardRVAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // recyclerview 연결
        showBoardRVAdapter = ShowBoardRVAdapter(communityDataList)
        binding.rvCommunityListShow.adapter = showBoardRVAdapter
        binding.rvCommunityListShow.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 클릭시 이벤트
        showBoardRVAdapter.setItemClickListener(object : ShowBoardRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(activity, BoardDetailActivity::class.java)
                startActivity(intent)
            }

        })

        return binding.root
    }
}