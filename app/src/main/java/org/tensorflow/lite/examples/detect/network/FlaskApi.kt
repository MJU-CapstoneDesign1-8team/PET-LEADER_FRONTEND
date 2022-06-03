package org.tensorflow.lite.examples.detect.network

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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
    ) : Call<String>

}