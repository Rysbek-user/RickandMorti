package com.example.rickandmorti

import android.util.Log
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
    val charactersData: LiveData<List<Character>?> = _charactersData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String> get() = _errorData

    fun getCharacters() {
        api.getCharacters().enqueue(object : retrofit2.Callback<BaseResponce> {
            override fun onResponse(call: Call<BaseResponce>, response: Response<BaseResponce>) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        _charactersData.postValue(it.characters)
                        Log.e("ololo", "onResponse: ${it.characters}", )
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponce>, t: Throwable) {
                _errorData.postValue(t.localizedMessage ?: "Unknown error")
            }
        })
    }
}