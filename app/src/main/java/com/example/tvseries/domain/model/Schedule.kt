package com.example.tvseries.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule (
    @SerializedName("time")
    val time: String,
    @SerializedName("days")
    val days: List<String>
) : Parcelable