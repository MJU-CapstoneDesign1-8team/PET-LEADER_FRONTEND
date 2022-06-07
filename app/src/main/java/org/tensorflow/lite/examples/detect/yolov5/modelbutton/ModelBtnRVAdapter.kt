package org.tensorflow.lite.examples.detect.yolov5.modelbutton

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.adapter.FreeBoardRVAdapter


class ModelBtnRVAdapter(val btnList: MutableList<ModelBtnData>) : RecyclerView.Adapter<ModelBtnRVAdapter.ModelHolder>() {

    lateinit var radioClickListener : OnItemClickListener
    val viewList: MutableList<View> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_button, parent, false)
        return ModelHolder(view)
    }

    override fun onBindViewHolder(holder: ModelHolder, position: Int) {
        Log.d("ModelData", "${btnList[position].toString()}")
        holder.bindItems(btnList[position])
        holder.setSelection(position)
        holder.itemView.setOnClickListener {
            radioClickListener.onClick(it, position, btnList[position])
            selectButton(position)
        }
    }

    override fun getItemCount(): Int = btnList.size


    //아이템 클릭 리스너
    interface OnItemClickListener {
        fun onClick(v: View, position: Int, model: ModelBtnData)
    }
    fun setItemClickListener(onItemClickListener: ModelBtnRVAdapter.OnItemClickListener) {
        this.radioClickListener = onItemClickListener
    }

    fun selectButton(position: Int) {
        Log.d("select", "$position")
        viewList.forEachIndexed { idx, v ->
            val title = v.findViewById<TextView>(R.id.model_btn_title)
            when (idx) {
                position -> title.setTextColor(Color.RED)
                else -> title.setTextColor(Color.WHITE)
            }
        }
    }

    inner class ModelHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val titleView = view.findViewById<TextView>(R.id.model_btn_title)
        private val imgView = view.findViewById<ImageView>(R.id.model_btn_img)

        fun bindItems(item : ModelBtnData) {
            Log.d("ModelData", "${item.title} ${item.btnImg}")
            titleView.text = item.title
            imgView.setImageResource(item.btnImg)
        }

        fun setSelection(pos: Int) {
            viewList.add(pos, view)
        }

    }
}