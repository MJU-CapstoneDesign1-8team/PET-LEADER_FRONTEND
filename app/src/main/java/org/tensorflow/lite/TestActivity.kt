package org.tensorflow.lite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.tensorflow.lite.examples.detect.R

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val T: Test = Test(applicationContext)
        T.init()
    }
}