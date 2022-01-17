package com.example.googlesheetdemo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by theapache64 : Aug 29 Sat,2020 @ 10:13
 */
data class Read(
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Description")
    val description: String,
)