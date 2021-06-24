package com.example.tvseries.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode (
    @SerializedName("showId")
    var showId: Long,
    @SerializedName("id")
    val id: Long,
    @SerializedName("number")
    val number: Int,
    @SerializedName("season")
    val season: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: Image? = null,
    @SerializedName("summary")
    val summary: String,
) : Parcelable