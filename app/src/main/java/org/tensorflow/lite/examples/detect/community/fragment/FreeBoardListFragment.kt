package org.tensorflow.lite.examples.detect.community.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.BoardDetailActivity
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.adapter.FreeBoardRVAdapter
import org.tensorflow.lite.examples.detect.databinding.FragmentFreeBoardListBinding

class FreeBoardListFragment : Fragment() {
    private lateinit var binding: FragmentFreeBoardListBinding
    private lateinit var freeBoardRVAdapter: FreeBoardRVAdapter
    private val communityDataList = mutableListOf<PostData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_free_board_list, container, false)

        val database = Firebase.database
        val myRef = database.getReference("message")
        myRef.setValue("Hello, World!")

        communityDataList.add(PostData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))
        communityDataList.add(PostData("제목 123123", "dasdasd","dasdasd","123123", "dsadasd"))

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