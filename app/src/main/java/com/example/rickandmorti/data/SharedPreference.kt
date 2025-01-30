package com.example.rickandmorti.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreference @Inject constructor(private val sharedPreference: SharedPreferences) {
 /*   private lateinit var sharedPreference: SharedPreferences
    fun unit(context: Context){
        sharedPreference = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }*/

    var isOnboardShown:Boolean
        get() = sharedPreference.getBoolean("onboard", false)
        set(value) = sharedPreference.edit().putBoolean("onboard", value).apply()
}