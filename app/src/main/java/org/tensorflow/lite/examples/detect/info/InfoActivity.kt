package org.tensorflow.lite.examples.detect.info

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_faq.*
import org.tensorflow.lite.examples.detect.AnimationFab
import org.tensorflow.lite.examples.detect.R

class InfoActivity : AppCompatActivity() {

    private lateinit var adapter : InfoAdapter
    private val context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val database = Firebase.database
        val infoDB = database.getReference("Info")
        val infoList: MutableList<InfoData> = mutableListOf()

        infoDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val info : InfoData? = it.getValue(InfoData::class.java)
                    if (info != null) {
                        infoList.add(info)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        val infoView = findViewById<RecyclerView>(R.id.info_list)
        adapter = InfoAdapter(infoList)
        infoView.adapter = adapter
        infoView.layoutManager = LinearLayoutManager(this)

        // 게시글 클릭 시 상세 게시글 페이지 전환
        adapter.setItemClickListener(object : InfoAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int, info: InfoData) {
                val intent = Intent(context, InfoSpecificActivity::class.java)
                intent.putExtra("info", info)
                startActivity(intent)
            }
        })

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