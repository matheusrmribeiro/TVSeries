package com.example.tvseries.domain.model

import com.google.gson.annotations.SerializedName

data class ShowResponse (
    @SerializedName("show")
    var show: Show,
)