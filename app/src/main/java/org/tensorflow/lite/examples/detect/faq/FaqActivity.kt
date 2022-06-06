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
import kotlinx.android.synthetic.main.activity_faq.*
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
        items.add(FAQData("입마개를 해야되는 견종을 모르겠어요.", "현재 견종정보 페이지를 통해 입마개를 해야하는 다섯 종의 맹견을 알려드리고 있습니다. 하지만 평소 관심있게 보지않으면 길가다가 잠시 마주치는 강아지를 바로 식별하는 것은 어렵기에 저희 앱에선 일상에서도 실시간으로 맹견을 탐지할 수 있는 기능을 제공하고 있습니다."))
        items.add(FAQData("실시간으로 확인하는 것과 동영상으로 신고하는 것의 차이가 뭔가요?", "현재 앱에 두가지로 감지를 가능하게 구현하였습니다. 안드로이드 어플리케이션의 한계로 인해 현재 저희가 학습시킨 모델의 인식률보다 낮은 수치를 보여주게 되어 동영상과 사진을 받아서 서버에서 감지를 더 정확하게 할 수 있게 기능을 분리하였습니다. 이 점 양해해주시기 바랍니다."))
        items.add(FAQData("커뮤니티에 내가 쓴 글을 보고 싶어요.", "회원가입 후 로그인을 하셔서 프로필을 누르시면 프로필 하단에 본인이 쓴 글을 모아서 볼 수 있는 버튼이 있습니다. 클릭하셔서 커뮤니티에 쓴 꿀팁과 정보를 한눈에 살펴보세요!"))
        items.add(FAQData("예전에 감지한 영상들을 볼 수 있나요?", "회원가입을 한 유저의 한해서 프로필에 들어가신다면 감지 목록을 확인하실 수 있습니다. 직접 찍은 영상에 강아지들이 위험에 노출되었는지 확인하세요!"))
        items.add(FAQData("하네스도 인식이 되나요?", "저희 탐지 모델안에 하네스도 인식 되도록 만들었습니다. 하네스, 목줄을 하지 않은 강아지들을 실시간으로 확인하고 신고하여 사람과 강아지가 안전하게 산책할 수 있는 문화를 만드는 것이 목표입니다."))
        items.add(FAQData("회원가입이 되지 않습니다.", "회원가입이 되지 않는다면, 이메일 형식에 맞게 ID를 기입했는지, 닉네임을 쓰지않았는지 확인 후 다시 시도해주세요. 만약 맞게 기입했음에도 회원가입이 안된다면 개발자에게 다시 연락하세요."))
        val rv = findViewById<RecyclerView>(R.id.faq_list)
        val adapter = FaqRVAdapter(items)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)


        // 뒤로가기 (홈)
        val backBtn = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            backBtn.hide(AnimationFab.addVisibilityChanged)
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