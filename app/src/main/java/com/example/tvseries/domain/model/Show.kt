package com.example.tvseries.domain.model

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Show (
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("genres")
    val genres: List<String> = listOf(),
    @SerializedName("status")
    val status: String,
    @SerializedName("schedule")
    val schedule: Schedule,
    @SerializedName("rating")
    val rating: Rating? = null,
    @SerializedName("image")
    val image: Image? = null,
    @SerializedName("summary")
    val summary: String,
    var loadedBitmap: Bitmap? = null
) : Parcelable