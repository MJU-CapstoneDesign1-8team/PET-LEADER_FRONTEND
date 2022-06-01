package com.example.petleader.FAQ

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R


class RVAdapter(val items: MutableList<FAQData>) : RecyclerView.Adapter<RVAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.faq_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item : FAQData) {
            //FAQ 질문과 답변 데이터 넣기
            val faqQues = itemView.findViewById<TextView>(R.id.faq_question)
            val faqAnsw = itemView.findViewById<TextView>(R.id.faq_answer)
            faqQues.text = item.question
            faqAnsw.text = item.answer

            //자세히보기 or 숨기기 기능
            val readMoreButton = itemView.findViewById<ImageView>(R.id.read_more_button)
            readMoreButton.setOnClickListener {
                when (faqAnsw.isVisible) {
                    true -> faqAnsw.visibility = View.GONE
                    false -> faqAnsw.visibility = View.VISIBLE
                }
            }
        }
    }
}

