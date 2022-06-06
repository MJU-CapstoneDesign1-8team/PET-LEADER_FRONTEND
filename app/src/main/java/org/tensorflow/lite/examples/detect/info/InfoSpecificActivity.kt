package org.tensorflow.lite.examples.detect.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        titleView.text = infoData.title
        simpleView.text = infoData.simpleInfo
        specificView.text = infoData.specificInfo

    }
}