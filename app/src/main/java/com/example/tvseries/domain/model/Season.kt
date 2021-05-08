package com.example.tvseries.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Season (
    @SerializedName("id")
    val id: Long,
    @SerializedName("number")
    val number: Int
) : Parcelable