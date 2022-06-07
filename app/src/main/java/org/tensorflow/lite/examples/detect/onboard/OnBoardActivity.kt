package org.tensorflow.lite.examples.detect.onboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.login.LoginActivity
import org.tensorflow.lite.examples.detect.login.RegisterActivity

class OnBoardActivity : AppCompatActivity() {
    var onBoardingViewPagerAdapter : OnBoardViewPagerAdapter? = null
    var tabLayout : DotsIndicator? = null
    var onBoardingViewPager : ViewPager? = null
    var next: TextView? = null
    var position = 0
    var sharedPreferences : SharedPreferences? = null
    var startOnboardBtn : Button? = null
    var loginOnboard : LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (restorePrefData()) {
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        setContentView(R.layout.activity_on_board)

        tabLayout = findViewById(R.id.dots_indicator)
        next = findViewById(R.id.tv_next_onboard)
        startOnboardBtn = findViewById(R.id.btn_startOnboard)
        loginOnboard = findViewById(R.id.onboard_login)

        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("목줄과 입마개는 필수", "방금 지나간 그 강아지 입마개 해야되나? \n목줄을 안한채로 위험하게 뛰어다닐때  \n실시간으로 분석된 영상을 만나보세요!", R.drawable.onboard11, "실시간 탐지"))
        onBoardingData.add(OnBoardingData("맹견에 대한 정보를 한눈에!", "공원에 갈때마다 걱정되는 당신.. \n 개만 보면 무섭다고요? \n 목줄을 안찬 강아지가 있다면?", R.drawable.onboard22, "맹견/정보"))
        onBoardingData.add(OnBoardingData("직접 겪은 꿀팁/정보", "견주들 모이는 장소 없나? \n하네스는 튼튼한게 제일이지! \n 커뮤니티를 통해 꿀팁을 공유하세요!", R.drawable.onboard33, "커뮤니티 기능"))
//        onBoardingData.add(OnBoardingData("맹견에 대한 정보를 한눈에!", "공원에 갈때마다 걱정되는 당신.. \n 개만 보면 무섭다고요? \n 목줄을 안찬 강아지가 있다면?", R.drawable.onboard11, "맹견/정보"))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        startOnboardBtn?.setOnClickListener {
            savePrefDate()
            val i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
        }

        loginOnboard?.setOnClickListener {
            savePrefDate()
            val i = Intent(applicationContext, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){

        onBoardingViewPager = findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter = OnBoardViewPagerAdapter(this,onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        tabLayout?.attachTo(onBoardingViewPager!!)
    }

    private fun savePrefDate() {
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("isFirstTimeRun", true)
        editor.apply()
    }

    private fun restorePrefData(): Boolean {
        sharedPreferences = applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences!!.getBoolean("isFirstTimeRun", false)
    }
}