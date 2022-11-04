package com.example.rickandmorty.domain.usecase

import android.app.Application
import com.example.rickandmorty.data.datasource.local.database.CharacterDatabase
import com.example.rickandmorty.data.model.CharacterResult
import com.example.rickandmorty.domain.repository.CharacterRepository
import com.example.rickandmorty.ui.viewstate.ViewState

class CharacterUseCase(application: Application) {
    private val characterDAO = CharacterDatabase.getDatabase(application).characterDao()
    private val characterRepository = CharacterRepository(characterDAO)

    suspend fun getAllCharacters(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharacters()
            ViewState.Success(characters)
        } catch (ex: Exception) {
            ViewState.Error(Exception("Não foi possível carregar a lista de personagens do Banco de Dados!"))
        }
    }

    suspend fun getAllCharactersFavorited(): ViewState<List<CharacterResult>> {
        return try {
            val characters = characterRepository.getAllCharactersFavorited()
            ViewState.Success(characters)
        } catch (ex: Exception) {
            ViewState.Error(Exception("Não foi possível carregar a lista de personagens favoritos!"))

        }
    }

    suspend fun updateCharacterFavorite(character: CharacterResult): ViewState<CharacterResult> {
        return try {
            characterRepository.updateCharacterFavorited(character)
            ViewState.Success(character)
        } catch (ex: Exception) {
            ViewState.Error(Exception("Não foi possível atualizar o personagem favoritado!"))
        }
    }

    suspend fun getAllCharactersNetwork(): ViewState<List<CharacterResult>> {
        return try {
            val response = characterRepository.geAllCharactersNetwork()
            characterRepository.insertAllCharactersDB(response.results)
            ViewState.Success(response.results)
        } catch (ex: Exception) {
            getAllCharacters()
        }
    }
}
