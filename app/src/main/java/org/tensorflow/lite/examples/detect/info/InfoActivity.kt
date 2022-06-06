package org.tensorflow.lite.examples.detect.info

import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petleader.faq.FaqRVAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import kotlinx.android.synthetic.main.activity_faq.*
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.faq.FAQData

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val infoList: MutableList<InfoData> = mutableListOf()
        infoList.add(InfoData("도사견", "재패니즈 마스티프, 혹은 '도사견'을 줄여서 도사라고도 부른다.\n" +
                "일본 도사(土佐(Tosa). 지금의 고치현) 지방에서 투견을 목적으로[2] 지역 재래종인 '시코쿠 견'에 불독·마스티프 등의 대형견을 교배시켜 만든 개. 몸은 강대하고 체고 55~80cm, 체중은 30~100kg까지 다양하다. 힘이 세고 인내심이 강하다. 일반적으로 알려진 황색뿐 아니라, 실제로는 백색, 흑색, 바둑, 브린들(호랑이 무늬) 등 다양한 털색을 갖고 있다.\n" +
                "원종인 개에 여러 개들을 섞어서 만들어진 잡종이라고 봐도 무방할 정도이다."))
        infoList.add(InfoData("아메리칸 핏볼테리어", "19세기 중반 영국에서 투견을 목적으로 테리어의 힘과 불독의 지구력을 얻기 위한 목적으로 만들어진 종이다. 그러나 현재는 영국에서 판매와 개인의 소유가 금지된 견종이다.[1] 수컷의 경우 최고 45~56cm에 체중은 16~40kg까지 나가며 암컷의 경우 최고 43~50 cm에 체중은 14~27 kg까지 나간다.[2] 미국에서 제일 사람을 많이 죽인 견종 1위라고 하며, 미국 내 다수의 판례에서도 일관되게 핏 불 테리어의 위험성을 널리 인정하고 있다."))
        infoList.add(InfoData("아메리칸 스태퍼드셔 테리어", "미국 원산의 개 품종이며 키는 43~48cm, 몸무게는 18~27kg 정도인 중형견에 속한다. 훈련하기는 쉬운 편에 속해 원산지인 미국에서는 상당히 많은 가정이 기르는 종이다. 이름의 첫글자와 중간 글자의 이니셜을 따 약자로 Amstaff(암스태프) 라 부르기도 한다."))
        infoList.add(InfoData("스태퍼드셔 볼 테리어", "스태퍼드셔 불 테리어(Staffordshire Bull Terrier) 또는 스태피(Staffie)는 잉글랜드 스태퍼드셔와 버밍엄 북부 지역의 기원이 되는, 털이 짧고 중간 크기의 견종이다. 이 종은 불독, 블랙 앤드 탠 테리어와의 교배를 통해 탄생하였다.\n" +
                "스태퍼드셔 불 테리어는 근육이 있고 매우 튼튼한 중간 크기의 개로서, 어깨높이로 36~41센티미터이고 몸무게는 13~17 킬로그램, 암컷의 경우 11~15 킬로그램에 달한다."))
        infoList.add(InfoData("로트 와일러", "독일 원산의 개 품종. 색상이 도베르만과 비슷하지만 전체적으로 체구가 더 크고 강인한 인상이다. 단단한 근육질의 몸에 큰 골격, 굵은 뼈대를 지니고, 특히 뒷다리는 도약력이 좋다.\n" +
                "겉보기와는 달리 무척 똑똑해서 훈련을 매우 빠르게 받아들인다.\n" +
                "독일어 발음은 로트바일러이고, 흔히 쓰는 로트와일러는 영어식 발음이다. 줄여서 로트나 로티라고 부르기도 한다. 수명이 8년으로 짧다. 어쩔 때는 10년 넘게 사는 개체들도 있다.\n" +
                "유럽과 미국에서는 경비견의 대명사로, 경비견 하면 도베르만과 함께 가장 먼저 떠오르는 견종이다."))


        val infoView = findViewById<RecyclerView>(R.id.info_list)
        val adapter = InfoAdapter(infoList)
        infoView.adapter = adapter
        infoView.layoutManager = LinearLayoutManager(this)


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