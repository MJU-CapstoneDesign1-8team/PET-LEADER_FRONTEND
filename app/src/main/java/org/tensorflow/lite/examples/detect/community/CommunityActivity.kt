package org.tensorflow.lite.examples.detect.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_community.bottomAppBar
import kotlinx.android.synthetic.main.activity_community.fab
import kotlinx.android.synthetic.main.activity_home.*
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.adapter.PostActivityStateAdapter

class CommunityActivity : AppCompatActivity() {
    private val tabTitles = arrayListOf(" 자유 ", " 관리 ", " 산책 ", " 자랑 ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        val viewPager2 = findViewById<ViewPager2>(R.id.viewpage2_comm)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout_comm)
        val createBoard = findViewById<FloatingActionButton>(R.id.fab)
        //val appbar = findViewById<AppBarLayout>(R.id.appbar)

        // 각 게시판별 탭 Fragment 설정
        viewPager2.adapter = PostActivityStateAdapter(this)

        // Tablayout에 문자 추가
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        // 게시글 작성 버튼
        createBoard.setOnClickListener {
            fab.hide(AnimationFab.addVisibilityChanged)
            Handler().postDelayed({
                val createBoardIntent = Intent(this, BoardCreateActivity::class.java)
                startActivity(createBoardIntent)
            }, 300)
        }

        // 네비게이션 바
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.4f))
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.4f))
            .build()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> { // 홈으로 돌아가기
                fab.hide(AnimationFab.addVisibilityChanged)
                Handler().postDelayed({
                    finish()
                }, 300)
            }
        }
        return true
    }

    override fun onStart() {
        // 애니메이션 작동
        super.onStart()
        Handler().postDelayed({
            fab.show()
        }, 450)
    }
}