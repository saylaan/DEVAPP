package com.epicture.app.models

import com.google.gson.annotations.SerializedName

class AccessToken {
    @SerializedName("access_token")
    var accessToken: String? = null
    @SerializedName("refresh_token")
    var refreshToken: String? = null
}