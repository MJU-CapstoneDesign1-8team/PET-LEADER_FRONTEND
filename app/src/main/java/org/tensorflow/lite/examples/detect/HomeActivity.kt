package org.tensorflow.lite.examples.detect

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_home.*
import org.tensorflow.lite.examples.detect.community.CommunityActivity
import org.tensorflow.lite.examples.detect.faq.FaqActivity
import org.tensorflow.lite.examples.detect.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_home.fab
import org.tensorflow.lite.examples.detect.info.InfoActivity
import org.tensorflow.lite.examples.detect.yolov5.DetectorActivity

class HomeActivity : AppCompatActivity() {
    //뒤로가기 연속 클릭 대기 시간
    private var mBackWait:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        val camBtn = findViewById<ImageView>(R.id.home_detect_button)
        val faqBtn = findViewById<ImageView>(R.id.home_faq_button)
        val infoBtn = findViewById<ImageView>(R.id.home_info_button)
        val commBtn = findViewById<ImageView>(R.id.home_community_button)

        faqBtn.setOnClickListener {
            fab.hide(AnimationFab.addVisibilityChanged)
            Handler().postDelayed({
                val faqIntent = Intent(this, FaqActivity::class.java)
                Log.d("click", "faq")
                startActivity(faqIntent)
            }, 300)
        }

        camBtn.setOnClickListener {
            fab.hide(AnimationFab.addVisibilityChanged)
            Handler().postDelayed({
                val camIntent = Intent(this, DetectChoiceActivity::class.java)
                Log.d("click", "detect")
                startActivity(camIntent)
            }, 300)
        }

        infoBtn.setOnClickListener {
            fab.hide(AnimationFab.addVisibilityChanged)
            Handler().postDelayed({
                val infoIntent = Intent(this, InfoActivity::class.java)
                startActivity(infoIntent)
                Log.d("click", "info")
            }, 300)
        }

        commBtn.setOnClickListener {
            fab.hide(AnimationFab.addVisibilityChanged)
            Handler().postDelayed({
                val commIntent = Intent(this, CommunityActivity::class.java)
                Log.d("click", "community")
                startActivity(commIntent)
            }, 300)
        }


        // 하단 바
        val bottomBarBackground = bottomAppBar.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel = bottomBarBackground.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(RoundedCornerTreatment()).setTopRightCornerSize(RelativeCornerSize(0.4f))
            .setTopLeftCorner(RoundedCornerTreatment()).setTopLeftCornerSize(RelativeCornerSize(0.4f))
            .build()

    }

    // 뒤로가기 버튼 클릭
    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >= 1500){ mBackWait = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else { finish() }
    }

    // 화면 아래 메뉴 바
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu_home, menu)
        return true
    }

    override fun onStart() {
        // 애니메이션 작동
        super.onStart()
        Handler().postDelayed({
            fab.show()
        }, 450)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> { // 홈으로 돌아가기
//                fab.hide(AnimationFab.addVisibilityChanged)
//                Handler().postDelayed({
//                    finish()
//                }, 300)
            }
            R.id.app_bar_profile ->{
                fab.hide(AnimationFab.addVisibilityChanged)
                Handler().postDelayed({
                    val profileIntent = Intent(this, ProfileActivity::class.java)
                    startActivity(profileIntent)
                }, 300)
            }
        }
        return true
    }


}