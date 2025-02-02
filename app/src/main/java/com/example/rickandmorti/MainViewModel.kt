package com.example.rickandmorti

import android.util.Log
import android.view.WindowInsetsAnimation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorti.data.CartoonApiService
import com.example.rickandmorti.data.model.BaseResponce
import com.example.rickandmorti.data.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: CartoonApiService,
) : ViewModel() {

    private val _charactersData = MutableLiveData<List<Character>?>()
    val charactersData: LiveData<List<Character>?> get() = _charactersData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String> get() = _errorData

    fun getCharacters() {
        api.getCharacters().enqueue(object : WindowInsetsAnimation.Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    _charactersData.postValue(response.body()?.characters)
                } else {
                    _errorData.postValue("Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _errorData.postValue(t.localizedMessage ?: "Unknown error")
            }
        })
    }
}