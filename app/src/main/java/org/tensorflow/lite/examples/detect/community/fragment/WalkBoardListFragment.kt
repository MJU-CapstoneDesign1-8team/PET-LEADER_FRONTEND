package org.tensorflow.lite.examples.detect.community.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostData
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

        communityDataList.add(PostData("제목 nnnnnn", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 nnnnnn", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 nnnnnn", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 nnnnnn", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 nnnnnn", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 nnnnnn", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 nnnnnn", "dasdasd","dasdasd","123123", "dsadasd"))

        // recyclerview 연결
        walkBoardRVAdapter = WalkBoardRVAdapter(communityDataList)
        binding.rvCommunityListWalk.adapter = walkBoardRVAdapter
        binding.rvCommunityListWalk.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 클릭시 이벤트
        walkBoardRVAdapter.setItemClickListener(object : WalkBoardRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(activity, BoardDetailActivity::class.java)
                startActivity(intent)
            }

        })

        return binding.root
    }
}