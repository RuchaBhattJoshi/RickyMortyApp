package com.example.rickmortyapp

//single source of truth
class Repository(private val apiService: ApiService) {

    //fetch from service
    fun getCharacters(page:String) = apiService.fetchCharacters(page)


}