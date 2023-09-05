package com.example.filmust.workdata

import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@IgnoreExtraProperties
data class LightMovie(
    @SerialName("id")
    val id : String? = null,
    @SerialName("titleText")
    val titleText : String? = null,
    @SerialName("genres")
    val genres : List<String>? = null,
    @SerialName("imageUrl")
    val imageUrl : String? = null,
    @SerialName("runtime")
    val runtime: Long? = null,
    @SerialName("releaseDate")
    val releaseDate: List<String>? = null, // 0 день 1 месяц 2 год
    @SerialName("rating")
    val rating: Long? = null,
    @SerialName("plot")
    val plot: String? = null
)
