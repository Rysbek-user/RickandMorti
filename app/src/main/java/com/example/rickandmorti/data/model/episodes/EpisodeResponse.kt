package com.example.rickandmorti.data.model.episodes

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val episodes: List<Episode>? = null
)