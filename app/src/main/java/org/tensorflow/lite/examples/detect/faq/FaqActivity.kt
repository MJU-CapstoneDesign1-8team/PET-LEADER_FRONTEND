package org.tensorflow.lite.examples.detect.faq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petleader.faq.FaqRVAdapter
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.faq.FAQData

class FaqActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

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
    }
}