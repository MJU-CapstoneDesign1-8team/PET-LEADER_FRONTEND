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
    }

    override fun getItemCount(): Int = items.size

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item : InfoData) {
            val infoTitle = itemView.findViewById<TextView>(R.id.info_title)
            val infoContent = itemView.findViewById<TextView>(R.id.info_content)
            infoTitle.text = item.title
            infoContent.text = item.content

            val readMoreButton = itemView.findViewById<ImageView>(R.id.info_read_more)
            readMoreButton.setOnClickListener {
                when (infoContent.isVisible) {
                    true -> infoContent.visibility = View.GONE
                    false -> infoContent.visibility = View.VISIBLE
                }
            }
        }
    }
}