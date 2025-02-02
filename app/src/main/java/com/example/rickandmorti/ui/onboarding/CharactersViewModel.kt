package com.example.rickandmorti.ui.onboarding

import android.view.WindowInsetsAnimation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.rickandmorti.ImageUtils
import com.example.rickandmorti.data.CartoonApiService
import com.example.rickandmorti.data.model.episodes.Episode
import com.example.rickandmorti.pagingSource.CharactersPagingSource
import com.example.rickandmorti.room.CharacterDao
import com.example.rickandmorti.room.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Response

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val api: CartoonApiService,
    private val characterDao: CharacterDao,
    private val pagingSource: CharactersPagingSource
) : ViewModel() {

    private val _charactersData = MutableLiveData<List<Character>>()
    val charactersData: LiveData<List<Character>> get() = _charactersData

    private val _errorData = MutableLiveData<String>()
    val errorData: LiveData<String> get() = _errorData

    private val _episodeName = MutableLiveData<String>()
    val episodeName: LiveData<String> get() = _episodeName

    private val episodeCache = mutableMapOf<String, String>()

    fun getCharacters() {
        api.getCharacters().enqueue(object : WindowInsetsAnimation.Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    response.body()?.characters?.let {
                        _charactersData.postValue(it)
                    } ?: run {
                        _errorData.postValue("No characters found")
                    }
                } else {
                    _errorData.postValue("Failed to fetch characters: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                _errorData.postValue(t.localizedMessage ?: "Unknown error")
            }
        })
    }

    fun getCharactersPaging(): LiveData<PagingData<Character>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 20 * 2
            ),
            pagingSourceFactory = {pagingSource}
        ).liveData
    }

    fun getEpisodeNameForCharacter(episodeUrl: String, callback: (String) -> Unit) {
        episodeCache[episodeUrl]?.let { cachedName ->
            callback(cachedName)
            return
        }

        api.getEpisodeName(episodeUrl).enqueue(object : WindowInsetsAnimation.Callback<Episode> {
            override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                val fetchedName = if (response.isSuccessful) {
                    response.body()?.name ?: "???"
                } else {
                    "???"
                }
                episodeCache[episodeUrl] = fetchedName
                callback(fetchedName)
            }

            override fun onFailure(call: Call<Episode>, t: Throwable) {
                callback("???")
            }
        })
    }


    fun saveViewedCharacter(character: Character) {
        character.episode?.firstOrNull()?.let { episodeUrl ->
            getEpisodeNameForCharacter(episodeUrl) { episodeName ->
                viewModelScope.launch(Dispatchers.IO) {
                    // Создаем объект CharacterEntity для сохранения в базу данных
                    val entity = CharacterEntity(
                        id = character.id,
                        name = character.name,
                        status = character.status ?: "Unknown",
                        species = character.species ?: "Unknown",
                        gender = character.gender ?: "Unknown",
                        location = character.location?.name ?: "Unknown",
                        firstEpisodeName = episodeName,
                        origin = character.origin?.name ?: "Unknown",
                        imageBase64 = character.image?.let { ImageUtils.urlToBase64(it) }
                    )

                    // Сохраняем в базу данных
                    characterDao.insertCharacter(entity)
                }
            }
        }
    }


    fun getViewedCharacters(): LiveData<List<CharacterEntity>> {
        return characterDao.getAllViewedCharacters()
    }


}