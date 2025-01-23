package com.example.rickandmorti.data.model


import com.google.gson.annotations.SerializedName

data class BaseResponce(
    @SerializedName("info")
    val info: Info? = null,
    @SerializedName("results")
    val characters: List<Character?>? = null
)