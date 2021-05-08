package com.example.tvseries.domain.model

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("code")
    var code: Int,

    @SerializedName("uid")
    var uid: String?,

    @SerializedName("auth_token")
    var authToken: String?,

    @SerializedName("client")
    var client: String?
)