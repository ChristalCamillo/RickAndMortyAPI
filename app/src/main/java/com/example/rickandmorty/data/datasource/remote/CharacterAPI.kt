package com.example.rickandmorty.data.datasource.remote

import com.example.rickandmorty.data.model.CharacterResponse
import retrofit2.http.GET

interface CharacterAPI {
    @GET("/character")
    suspend fun getAllCharactersNetwork(): CharacterResponse
}