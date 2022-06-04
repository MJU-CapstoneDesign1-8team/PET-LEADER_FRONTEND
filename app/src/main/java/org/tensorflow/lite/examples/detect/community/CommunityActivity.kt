package org.tensorflow.lite.examples.detect.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.adapter.PostActivityStateAdapter

class CommunityActivity : AppCompatActivity() {
    private val tabTitles = arrayListOf(" 자유 ", " 관리 ", " 산책 ", " 자랑 ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val viewPager2 = findViewById<ViewPager2>(R.id.viewpage2_comm)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout_comm)
        //val appbar = findViewById<AppBarLayout>(R.id.appbar)

        // 뷰 페이지
        viewPager2.adapter = PostActivityStateAdapter(this)


        // Tablayout에 문자 추가
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabTitles[position]

        }.attach()
    }
}