package org.tensorflow.lite.examples.detect.community.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab

class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.tv_free_list_item_title)
    private val nickname: TextView = itemView.findViewById(R.id.tv_free_list_item_writerNickname)
    private val date: TextView = itemView.findViewById(R.id.tv_free_list_item_date)
    private val img : ImageView = itemView.findViewById(R.id.rv_imageView)
    private val mainText : TextView = itemView.findViewById(R.id.rv_textMain)
    private val postId: TextView = itemView.findViewById(R.id.tv_free_list_item_post_id)

    fun bindItems(item: PostData){
        //img.setImageDrawable(R.drawable.veterinary)
        img.setImageResource(when (item.tab) {
            PostTab.FREE -> R.drawable.board
            PostTab.CARE -> R.drawable.veterinary
            PostTab.WALK -> R.drawable.dog
            PostTab.SHOW -> R.drawable.pawprint
            else -> R.drawable.pawprint
        })
        mainText.text = item.tab.tabName
        title.text = item.title
        nickname.text = item.nickname
        date.text = item.time
        postId.text = item.postId
    }
}