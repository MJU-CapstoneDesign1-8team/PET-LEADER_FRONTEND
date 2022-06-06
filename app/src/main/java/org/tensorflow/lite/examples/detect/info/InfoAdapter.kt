package org.tensorflow.lite.examples.detect.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R

class InfoAdapter(val items: MutableList<InfoData>) : RecyclerView.Adapter<InfoAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: InfoAdapter.Holder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item : InfoData) {
            val infoTitle = itemView.findViewById<TextView>(R.id.info_title)
            val infoContent = itemView.findViewById<TextView>(R.id.info_content)
            val infoImg = itemView.findViewById<ImageView>(R.id.info_dog_img)
            infoTitle.text = item.title
            infoContent.text = item.simpleInfo
            infoImg.setImageResource(when(item.title) {
                "도사견" -> R.drawable.fierce_dog1
                "아메리칸 핏볼테리어" -> R.drawable.fierce_dog2
                "아메리칸 스태퍼드셔 테리어" -> R.drawable.fierce_dog3
                "스태퍼드셔 볼 테리어" -> R.drawable.fierce_dog4
                "로트 와일러" -> R.drawable.fierce_dog5
                else -> R.drawable.board123
            })

            val readMoreButton = itemView.findViewById<ImageView>(R.id.info_read_more)
            readMoreButton.setOnClickListener {
                when (infoContent.isVisible) {
                    true -> infoContent.visibility = View.GONE
                    false -> infoContent.visibility = View.VISIBLE
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int, info: InfoData)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener
}