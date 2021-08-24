package com.example.rickmortyapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import com.example.rickmortyapp.Character


class MainViewModel(
    private val repository: Repository = Repository(ApiClient.apiService)
): ViewModel() {

    init {
        fetchCharacter()
    }

    //can't modify outside
    private var _characterLiveData = MutableLiveData<List<Character>>()
    //only read/observe can't change
    val characterLiveData: LiveData<List<Character>>
        get() = _characterLiveData

        private fun fetchCharacter(){

            val client = repository.getCharacters("1")
            client.enqueue(object : retrofit2.Callback<CharacterResponse>{
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    if(response.isSuccessful){
                        _characterLiveData.postValue(response.body()?.result)
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    Log.d("error", ""+t.message)
                }

            })
        }



}