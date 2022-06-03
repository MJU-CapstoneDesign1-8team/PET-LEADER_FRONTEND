package org.tensorflow.lite.examples.detect.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        const val BASE_URL = "http://10.0.2.2:5000" // 주소

        fun <T> create(apiService : Class<T>) : T {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(apiService)
        }
    }
}