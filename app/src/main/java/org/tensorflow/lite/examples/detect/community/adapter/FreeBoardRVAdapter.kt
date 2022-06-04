package org.tensorflow.lite.examples.detect.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.CommunityData

class FreeBoardRVAdapter(private val communityList: MutableList<CommunityData>) :
    RecyclerView.Adapter<FreeBoardRVAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_free_comm, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
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

        fun bindItems(item: CommunityData){
            //img.setImageDrawable(R.drawable.veterinary)

            img.setImageResource(R.drawable.pawprint)
            mainText.text = "자유"
            title.text = item.title
            nickname.text = item.nickname
            date.text = item.time
        }
    }

}