package org.tensorflow.lite.examples.detect.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.camera.VerityData
import org.tensorflow.lite.examples.detect.community.PostData
import org.tensorflow.lite.examples.detect.community.PostTab

class ProfileResultRVAdapter(val items  : MutableList<VerityData>) : RecyclerView.Adapter<ProfileResultRVAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileResultRVAdapter.Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_result_verity, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: ProfileResultRVAdapter.Holder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    private lateinit var itemClickListener : OnItemClickListener

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_free_list_item_title)
        private val nickname: TextView = itemView.findViewById(R.id.tv_free_list_item_writerNickname)
        private val date: TextView = itemView.findViewById(R.id.tv_free_list_item_date)
        private val img : ImageView = itemView.findViewById(R.id.rv_imageView)
        private val mainText : TextView = itemView.findViewById(R.id.rv_textMain)
        private val postId: TextView = itemView.findViewById(R.id.tv_free_list_item_post_id)

        fun bindItems(item: VerityData){
            //img.setImageDrawable(R.drawable.veterinary)


        }
    }
}