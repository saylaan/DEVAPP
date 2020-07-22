package com.epicture.app.models

import com.google.gson.annotations.SerializedName

class ImageImgur {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("views")
    var views = 0
    @SerializedName("link")
    var link: String? = null
    @SerializedName("favorite")
    var favorite = false
    @SerializedName("in_gallery")
    var in_gallery = false
    @SerializedName("in_most_viral")
    var in_most_viral = false
}