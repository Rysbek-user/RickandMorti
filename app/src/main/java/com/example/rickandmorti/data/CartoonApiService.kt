package com.example.rickandmorti.data

import com.example.rickandmorti.data.model.BaseResponce
import retrofit2.Call
import retrofit2.http.GET

interface CartoonApiService {

    @GET("character")
    fun getCharacters(): Call<BaseResponce>

}