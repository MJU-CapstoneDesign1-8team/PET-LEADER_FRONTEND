package org.tensorflow.lite.examples.detect.faq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petleader.faq.FaqRVAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import kotlinx.android.synthetic.main.activity_home.*
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R

class FaqActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        val myBuffer: MutableList<Byte> = mutableListOf<Byte>()
        val items = mutableListOf<FAQData>()
        items.add(FAQData("하네스도 인식하나요", "인식합니다. 검사 페이지에서 목줄 인식이 하네스 인식도 동시에 진행하고 있습니다."))
        items.add(FAQData("회원가입이 되지 않습니다", "회원가입이 되지 않는다면, 해당 연락처로 연락을 주시면 해결해드리겠습니다."))
        items.add(FAQData("견종 정보는 어디에서 얻을 수 있나요", "홈으로 들어가 견종 정보 페이지로 가는 버튼이 존재합니다."))
        items.add(FAQData("하네스도 인식하나요", "인식합니다. 검사 페이지에서 목줄 인식이 하네스 인식도 동시에 진행하고 있습니다."))
        items.add(FAQData("회원가입이 되지 않습니다", "회원가입이 되지 않는다면, 해당 연락처로 연락을 주시면 해결해드리겠습니다."))
        items.add(FAQData("견종 정보는 어디에서 얻을 수 있나요", "홈으로 들어가 견종 정보 페이지로 가는 버튼이 존재합니다."))
        val rv = findViewById<RecyclerView>(R.id.faq_list)
        val adapter = FaqRVAdapter(items)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        // 뒤로가기 (홈)
        val backBtn = findViewById<FloatingActionButton>(R.id.fab)
        backBtn.setOnClickListener {
            fab.hide(AnimationFab.addVisibilityChanged)
            Handler().postDelayed({
                finish()
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

    override fun onStart() {
        // 애니메이션 작동
        super.onStart()
        Handler().postDelayed({
            fab.show()
        }, 450)
    }
}