package com.example.tvseries.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Rating(
    @SerializedName("average")
    val average: @RawValue Double
) : Parcelable