package org.tensorflow.lite.examples.detect.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.PostData

class ProfileAdapter(val items: MutableList<PostData>) : RecyclerView.Adapter<ProfileAdapter.Holder>() {
    private lateinit var itemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ProfileAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_free_comm, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: ProfileAdapter.Holder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItemCount(): Int = items.size

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_free_list_item_title)
        private val nickname: TextView = itemView.findViewById(R.id.tv_free_list_item_writerNickname)
        private val date: TextView = itemView.findViewById(R.id.tv_free_list_item_date)
        private val img : ImageView = itemView.findViewById(R.id.rv_imageView)
        private val mainText : TextView = itemView.findViewById(R.id.rv_textMain)
        private val postId: TextView = itemView.findViewById(R.id.tv_free_list_item_post_id)

        fun bindItems(item: PostData){
            //img.setImageDrawable(R.drawable.veterinary)
            img.setImageResource(R.drawable.pawprint)
            mainText.text = item.tab.tabName
            title.text = item.title
            nickname.text = item.nickname
            date.text = item.time
            postId.text = item.postId
        }
    }
}