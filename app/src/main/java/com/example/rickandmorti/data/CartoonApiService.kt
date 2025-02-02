package com.example.rickandmorti.data

import android.telecom.Call
import androidx.room.Query
import com.example.rickandmorti.data.model.episodes.Episode
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface CartoonApiService {

    @GET("character")
    fun getCharacters(): Call<BaseResponse>

    @GET("character")
    suspend fun getCharactersPaging(
        @Query("page") page: Int
    ): Response<BaseResponse>

    @GET
    fun getEpisodeName(@Url url: String): Call<Episode>
}