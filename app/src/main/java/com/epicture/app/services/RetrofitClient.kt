@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.epicture.app.services

import com.epicture.app.utils.App
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val BASE_URL = "https://api.imgur.com/3/"
    val TOKEN : String? = App.instance?.getBearerToken()
    private val gson : Gson = GsonBuilder().create()
    private var retrofit: Retrofit? = null

    private fun setAuthHeader(builder: Request.Builder) {
        if (TOKEN != null) builder.header("Authorization", TOKEN)
    }

    private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .method(original.method(), original.body())
                setAuthHeader(requestBuilder)
                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()

    fun <T> createService(serviceClass: Class<T>?): T? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit?.create(serviceClass)
    }
}

//    val instance: ApiInterface by lazy {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//        retrofit.create(ApiInterface::class.java)
//    }