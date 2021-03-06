package org.tensorflow.lite.examples.detect.profile.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import org.tensorflow.lite.examples.detect.R
import org.tensorflow.lite.examples.detect.camera.VerityData
import java.io.File

class ProfileDetectRVAdapter(val items  : MutableList<VerityData>) : RecyclerView.Adapter<ProfileDetectRVAdapter.Holder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_result_verity, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
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
        private val rstBreed: TextView = itemView.findViewById(R.id.tv_verify_breed)
        private val rstMuzzle: TextView = itemView.findViewById(R.id.tv_verify_muzzle)
        private val rstSafety: TextView = itemView.findViewById(R.id.tv_verify_safety)
//        private val title: TextView = itemView.findViewById(R.id.tv_verify_title)

        private val date: TextView = itemView.findViewById(R.id.tv_verify_date)
        private val imgType : TextView = itemView.findViewById(R.id.tv_verify_img_type)
        private val img : ImageView = itemView.findViewById(R.id.tv_verify_img)
        private val imgList : MutableMap<String, Bitmap> = mutableMapOf()

        fun bindItems(item: VerityData){
            //?????? ?????? ??????
            rstBreed.text = "??????: " + getDetectDetect(item.resultBreed)
            rstMuzzle.text = "?????????: " + getDetectDetect(item.resultMuzzle)
            rstSafety.text = "??????: " + getDetectDetect(item.resultSafety)
            date.text = item.date

            //?????? ????????? ??????
            val list : List<String> = listOf("breed", "muzzle", "safety")
            list.forEach { detectType ->
                loadImage(item.verifyId, detectType)
            }

            //????????? ?????? ??? ?????? ???????????? ??????
            var imgClickListener = View.OnClickListener {
                val type : String = imgType.text.toString()
                Log.d("ImgClick", type)
                when (type) {
                    "??????" -> {
                        img.setImageBitmap(imgList["muzzle"])
                        imgType.text = "?????????"
                    }
                    "?????????" -> {
                        img.setImageBitmap(imgList["safety"])
                        imgType.text = "??????"
                    }
                    "??????" -> {
                        img.setImageBitmap(imgList["breed"])
                        imgType.text = "??????"
                    }
                    else -> {
                        img.setImageResource(R.drawable.analysis)
                    }
                }
            }
            img.setOnClickListener(imgClickListener)

        }

        fun getDetectDetect(result : Boolean) = when (result) {
            true -> "??????"
            false -> "?????????"
            else -> "?????????"
        }

        fun loadImage(id: String, file: String) {
            val storageRef = FirebaseStorage.getInstance().reference.child("image/${id}/${file}")
            val localFile = File.createTempFile("tempFile", "jpeg")

            storageRef.getFile(localFile).addOnSuccessListener {
//                Log.d("Storage", bitmap.toString())
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                imgList[file] = bitmap
                if (file == "breed") {
                    img.setImageBitmap(bitmap)
                    imgType.text = "??????"
                }
            }.addOnFailureListener {
                Log.d("Storage", storageRef.toString())
                Log.d("Storage", it.toString())
            }
        }
    }
}