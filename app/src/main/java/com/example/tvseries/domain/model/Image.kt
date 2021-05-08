package com.example.tvseries.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    @SerializedName("medium")
    val medium: String,
    @SerializedName("original")
    val original: String
) : Parcelable