package com.epicture.app.services

import com.epicture.app.models.ImageImgurList
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

//    @Headers("Authorization: Client-ID 1eec504f9fb40b1")
    @GET("gallery/search/q_type=png")
    fun getImages(@Query("q") q: String): Call<ImageImgurList>

    @GET("gallery/hot/top/1/day")
    fun getAllImages(): Call<ImageImgurList>

    @GET("gallery/hot/time/1/year")
    fun getLatestImages(): Call<ImageImgurList>

    @GET("account/me/images")
    fun getUserImages(): Call<ImageImgurList>

    @GET("account/me/favorites")
    fun getUserFavorites(): Call<ImageImgurList>

    @Multipart
    @POST("image")
    fun postImage(@Part file: MultipartBody.Part, @Query("title") title: String): Call<ResponseBody>

    @POST("image/{id}/favorite")
    fun favImage(@Path("id") id: String?): Call<ResponseBody>

}
