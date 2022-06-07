package org.tensorflow.lite.examples.detect.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import kotlinx.android.synthetic.main.activity_faq.*
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R

class ReportInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_info)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        setSupportActionBar(bottomAppBar)

        val items = mutableListOf<ReportInfoData>()
        items.add(ReportInfoData("과태료 안내", "목줄 미착용 시 : (1차) 20만원, (2차) 30만원, (3차 이상) 50만원\n" +
                "·인식표 미착용 시 : (1차) 5만원, (2차) 10만원, (3차 이상) 20만원\n" +
                "·맹견은 목줄·입마개 미착용 시 : (1차) 100만원, (2차) 200만원, (3차 이상) 300만원\n-반려견과 함께 외출 시, 배변봉투를 챙겨주세요.\n" +
                "※위반 시 과태료 : (1차) 5만원, (2차) 7만원, (3차 이상) 10만원\n" +
                "\n" +
                "-맹견소유자는 법정 교육 이수, 책임보험 가입이 의무입니다.\n" +
                "*맹견 종류 : 도사견, 아메리카 핏불테리어, 아메리칸 스태퍼드셔 테리어, 스태퍼드셔 불 테리어, 로트와일러 등 5종과 그 잡종의 개\n" +
                "※위반 시 과태료 : (1차) 100만원, (2차) 200만원, (3차 이상) 300만원\n"))
        items.add(ReportInfoData("맹견소유자의 법정 교육 이수", "동물보호복지온라인시스템(apms.epis.or.kr) 에서 확인하세요!"))
        items.add(ReportInfoData("신고 기준", "2019년 기준 동물보호법 지정 맹견 5종\n" +
                "도사견, 아메리칸 핏불테리어, 아메리칸 스태퍼드셔테리어, 그태퍼드셔 불테리어, 로트와일러 외 그의 믹스종까지 해당\n" +
                "\n" +
                "맹견 외출은 반드시 만 14세 이상 반려인과 동행\n" +
                "어린이집·유치원·초등학교에는 출입 금지\n" +
                "맹견 반드시 목줄, 입마개 착용 등 안전장치 필수\n"))
        items.add(ReportInfoData("주의할 점", "-타인의 반려견의 눈을 빤히 응시하지 말아 주세요. 공격의 신호로 받아들여질 수 있어요.\n" +
                "-타인의 반려견을 만지기 전 견주의 동의를 먼저 구해야 합니다.\n" +
                "-타인의 반려견에게 견주의 동의 없이 먹이를 주면 안 됩니다.\n" +
                "-타인의 반려견에게 갑자기 다가가거나 소리를 지르지 말아 주세요.\n"))
        val rv = findViewById<RecyclerView>(R.id.report_info_list)
        val adapter = ReportInfoRVAdapter(items)
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