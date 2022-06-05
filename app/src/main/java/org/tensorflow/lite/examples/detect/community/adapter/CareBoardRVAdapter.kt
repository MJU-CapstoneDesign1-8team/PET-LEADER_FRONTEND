package org.tensorflow.lite.examples.detect.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.PostData

class CareBoardRVAdapter(private val communityList: MutableList<PostData>) :
    RecyclerView.Adapter<CareBoardRVAdapter.CustomViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CareBoardRVAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_free_comm, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CareBoardRVAdapter.CustomViewHolder, position: Int) {
        holder.bindItems(communityList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener


    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_free_list_item_title)
        private val nickname: TextView = itemView.findViewById(R.id.tv_free_list_item_writerNickname)
        private val date: TextView = itemView.findViewById(R.id.tv_free_list_item_date)
        private val img : ImageView = itemView.findViewById(R.id.rv_imageView)
        private val mainText : TextView = itemView.findViewById(R.id.rv_textMain)

        fun bindItems(item: PostData){
            img.setImageResource(R.drawable.veterinary)
            mainText.text = "관리"
            title.text = item.title
            nickname.text = item.nickname
            date.text = item.time
        }
    }
}