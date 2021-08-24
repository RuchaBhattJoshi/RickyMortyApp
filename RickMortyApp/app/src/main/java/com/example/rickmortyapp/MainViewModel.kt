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


    //can't modify outside
    private var _characterLiveData = MutableLiveData<ScreenState<List<Character>?>>()
    //only read/observe can't change
    val characterLiveData: LiveData<ScreenState<List<Character>?>>
        get() = _characterLiveData


    init {
        fetchCharacter()
    }

        private fun fetchCharacter(){

            val client = repository.getCharacters("1")
            _characterLiveData.postValue(ScreenState.Loading(null))
            client.enqueue(object : retrofit2.Callback<CharacterResponse>{
                override fun onResponse(
                    call: Call<CharacterResponse>,
                    response: Response<CharacterResponse>
                ) {
                    if(response.isSuccessful){
                        _characterLiveData.postValue(ScreenState.Success(response.body()?.result))
                    }else{
                        _characterLiveData.postValue(ScreenState.Error(response.code().toString()))
                    }
                }

                override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                    Log.d("error", ""+t.message)
                    _characterLiveData.postValue(ScreenState.Error(t.message.toString(),null))


                }

            })
        }



}