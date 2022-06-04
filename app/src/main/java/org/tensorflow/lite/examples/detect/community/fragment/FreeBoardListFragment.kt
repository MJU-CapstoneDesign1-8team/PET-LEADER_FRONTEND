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
import org.tensorflow.lite.examples.detect.community.CommunityData
import org.tensorflow.lite.examples.detect.community.adapter.FreeBoardRVAdapter
import org.tensorflow.lite.examples.detect.databinding.FragmentFreeBoardListBinding

class FreeBoardListFragment : Fragment() {
    private lateinit var binding: FragmentFreeBoardListBinding
    private lateinit var freeBoardRVAdapter: FreeBoardRVAdapter
    private val communityDataList = mutableListOf<CommunityData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_free_board_list, container, false)

        communityDataList.add(CommunityData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(CommunityData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(CommunityData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(CommunityData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(CommunityData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(CommunityData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(CommunityData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))

        // recyclerview 연결
        freeBoardRVAdapter = FreeBoardRVAdapter(communityDataList)
        binding.rvCommunityListFree.adapter = freeBoardRVAdapter
        binding.rvCommunityListFree.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // 클릭시 이벤트
        freeBoardRVAdapter.setItemClickListener(object : FreeBoardRVAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(activity, BoardDetailActivity::class.java)
                startActivity(intent)
            }

        })

        return binding.root
    }

}