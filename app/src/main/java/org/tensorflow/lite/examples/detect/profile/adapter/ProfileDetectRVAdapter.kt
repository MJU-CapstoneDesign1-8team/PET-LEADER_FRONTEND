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
            //인식 결과 출력
            rstBreed.text = "견종: " + getDetectDetect(item.resultBreed)
            rstMuzzle.text = "입마개: " + getDetectDetect(item.resultMuzzle)
            rstSafety.text = "목줄: " + getDetectDetect(item.resultSafety)
            date.text = item.date

            //인식 이미지 출력
            val list : List<String> = listOf("breed", "muzzle", "safety")
            list.forEach { detectType ->
                loadImage(item.verifyId, detectType)
            }

            //이미지 클릭 시 다른 화면으로 전환
            var imgClickListener = View.OnClickListener {
                val type : String = imgType.text.toString()
                Log.d("ImgClick", type)
                when (type) {
                    "견종" -> {
                        img.setImageBitmap(imgList["muzzle"])
                        imgType.text = "입마개"
                    }
                    "입마개" -> {
                        img.setImageBitmap(imgList["safety"])
                        imgType.text = "목줄"
                    }
                    "목줄" -> {
                        img.setImageBitmap(imgList["breed"])
                        imgType.text = "견종"
                    }
                    else -> {
                        img.setImageResource(R.drawable.analysis)
                    }
                }
            }
            img.setOnClickListener(imgClickListener)

        }

        fun getDetectDetect(result : Boolean) = when (result) {
            true -> "인식"
            false -> "불인식"
            else -> "미확인"
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
                    imgType.text = "견종"
                }
            }.addOnFailureListener {
                Log.d("Storage", storageRef.toString())
                Log.d("Storage", it.toString())
            }
        }
    }
}