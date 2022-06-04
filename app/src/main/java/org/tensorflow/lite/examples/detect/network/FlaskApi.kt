package org.tensorflow.lite.examples.detect.network

import android.media.Image
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface FlaskApi {
    @Multipart
    @POST("/upload")
    fun postFile(
        @Part("userId") userId: String,
        @Part file: MultipartBody.Part?
    ): Call<FlaskDto>

    // 테스트 통신
    @GET("/test")
    fun getTest(
    ): Call<String>


    @POST("/downloads")
    fun getImage(
        @Body path : String
    ):Call<ResponseBody>

}