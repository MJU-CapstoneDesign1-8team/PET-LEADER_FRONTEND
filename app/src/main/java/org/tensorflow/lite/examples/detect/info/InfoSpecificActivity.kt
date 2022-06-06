package org.tensorflow.lite.examples.detect.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.tensorflow.lite.examples.detect.R

class InfoSpecificActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_specific)

        val infoData = intent.getSerializableExtra("info") as InfoData


        val titleView = findViewById<TextView>(R.id.infos_title)
        val simpleView = findViewById<TextView>(R.id.infos_simple)
        val specificView = findViewById<TextView>(R.id.infos_specific)
        val imgView = findViewById<ImageView>(R.id.infos_img)

        titleView.text = infoData.title
        simpleView.text = infoData.simpleInfo
        specificView.text = infoData.specificInfo
        imgView.setImageResource(when(infoData.title) {
            "도사견" -> R.drawable.fierce_dog1
            "아메리칸 핏볼테리어" -> R.drawable.fierce_dog2
            "아메리칸 스태퍼드셔 테리어" -> R.drawable.fierce_dog3
            "스태퍼드셔 볼 테리어" -> R.drawable.fierce_dog4
            "로트 와일러" -> R.drawable.fierce_dog5
            else -> R.drawable.board123
        })

    }
}