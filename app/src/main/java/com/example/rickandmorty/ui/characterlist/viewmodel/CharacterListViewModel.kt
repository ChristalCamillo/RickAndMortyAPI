package com.example.rickandmorty.ui.characterlist.viewmodel

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

class CharacterListViewModel(application: Application): AndroidViewModel(application) {
    private val characterUseCase = CharacterUseCase(application)
    val characterListState = MutableLiveData<ViewState<List<CharacterResult>>>()

    fun getAllCharactersNetwork(){
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    characterUseCase.getAllCharactersNetwork()
                }
                characterListState.value = response
            }
            catch (ex: Exception){
                characterListState.value = ViewState.Error(Throwable("Não foi possível carregar a lista"))
            }
        }
    }
}