package com.example.rickandmorty.domain.repository

import com.example.rickandmorty.data.datasource.local.dao.CharacterDAO
import com.example.rickandmorty.data.datasource.remote.RetrofitService
import com.example.rickandmorty.data.model.CharacterResponse
import com.example.rickandmorty.data.model.CharacterResult

class CharacterRepository(private val characterDAO: CharacterDAO) {

    suspend fun getAllCharacters(): List<CharacterResult> = characterDAO.getAllCharacters()

    suspend fun insertAllCharactersDB(listCharacters: List<CharacterResult>) {
        characterDAO.insertAllCharacters(listCharacters)
    }

    suspend fun getAllCharactersFavorited(): List<CharacterResult> = characterDAO.getAllCharactersFavorited()

    suspend fun updateCharacterFavorited(character: CharacterResult){
        characterDAO.updateCharacterFavorite(character)
    }

    suspend fun geAllCharactersNetwork(): CharacterResponse {
        return RetrofitService.apiService.getAllCharactersNetwork()
    }
}