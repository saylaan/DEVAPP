package com.epicture.app.models

import com.google.gson.annotations.SerializedName

class ImageImgurList {
    @SerializedName("data")
    var listImageImgur: ArrayList<ImageImgur>? = null

    fun getImages(): ArrayList<ImageImgur>? {
        return this.listImageImgur
    }

    fun setImages(listImageImgur : ArrayList<ImageImgur>) {
        this.listImageImgur = listImageImgur
    }
}