package com.example.newsreader.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavSource (
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var url: String? = null,
    var category: String? = null,
    var language: String? = null,
    var country: String? = null,
    var isFav: Int = 1
) : Parcelable