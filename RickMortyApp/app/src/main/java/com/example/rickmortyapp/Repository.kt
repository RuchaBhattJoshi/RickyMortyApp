package com.example.rickmortyapp

import com.example.rickmortyapp.network.ApiService

//single source of truth
class Repository(private val apiService: ApiService) {

    //fetch from service
    suspend fun getCharacters(page:String) = apiService.fetchCharacters(page)


}