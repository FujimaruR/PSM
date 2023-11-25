package com.example.psm

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private const val BASE_URL = "https://vetbuddylmad.000webhostapp.com/"

    val instance: ApiInterface by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")

                // Agregar un check para el cuerpo de la solicitud antes de construir la solicitud
                original.body?.let { requestBody ->
                    requestBuilder.method(original.method, requestBody)
                }

                val request = requestBuilder.build()
                try {
                    val response = chain.proceed(request)
                    val responseBodyString = response.peekBody(Long.MAX_VALUE).string()
                    Log.d("Response Body", responseBodyString)
                    response.newBuilder().body(ResponseBody.create(response.body?.contentType(), responseBodyString)).build()
                } catch (e: Exception) {
                    throw e
                }
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiInterface::class.java)
    }
}