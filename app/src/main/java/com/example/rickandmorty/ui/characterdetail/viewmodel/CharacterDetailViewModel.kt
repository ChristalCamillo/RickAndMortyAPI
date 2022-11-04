package com.example.rickandmorty.ui.characterdetail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.model.CharacterResult
import com.example.rickandmorty.domain.usecase.CharacterUseCase
import com.example.rickandmorty.ui.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(application: Application): AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    private val favoriteCharacterState = MutableLiveData<ViewState<CharacterResult>>()

    fun updateCharacterFavorite(character: CharacterResult){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    characterUseCase.updateCharacterFavorite(character)
                }
                favoriteCharacterState.value = response
            }catch (e: Exception){
                favoriteCharacterState.value = ViewState.Error(Exception("Erro na favoritagem"))
            }
        }
    }
}