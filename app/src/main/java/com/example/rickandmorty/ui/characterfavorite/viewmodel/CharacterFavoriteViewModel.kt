package com.example.rickandmorty.ui.characterfavorite.viewmodel

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

class CharacterFavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val characterUseCase = CharacterUseCase(application)
    val characterFavoriteState = MutableLiveData<ViewState<List<CharacterResult>>>()

    fun getAllCharactersFavorited(){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO){
                    characterUseCase.getAllCharactersFavorited()
                }
                characterFavoriteState.value = response
            }catch (e: Exception){
                characterFavoriteState.value = ViewState.Error(Exception("Erro na exibição da lista de personagens favoritados"))
            }
        }
    }

}