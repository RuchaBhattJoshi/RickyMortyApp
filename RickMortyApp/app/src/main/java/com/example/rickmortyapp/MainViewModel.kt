package com.example.rickmortyapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import retrofit2.Call
import retrofit2.Response
import com.example.rickmortyapp.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


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
            viewModelScope.launch(Dispatchers.IO) {
                Log.d("Viewmodel", Thread.currentThread().name)
                try{
                    val client = repository.getCharacters("1")
                    _characterLiveData.postValue(ScreenState.Success(client.result))
                }catch (e:Exception){
                    _characterLiveData.postValue(ScreenState.Error(e.message.toString(),null))
                }
            }
            _characterLiveData.postValue(ScreenState.Loading(null))

        }



}