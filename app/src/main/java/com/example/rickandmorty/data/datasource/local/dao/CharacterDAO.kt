package com.example.rickandmorty.data.datasource.local.dao

import androidx.room.*
import com.example.rickandmorty.data.model.CharacterResult

@Dao
interface CharacterDAO {
    @Query("SELECT * FROM characters ORDER BY id ASC")
    fun getAllCharacters(): List<CharacterResult>

    @Query("SELECT * FROM characters WHERE isFavorite = 1")
    fun getAllCharactersFavorited(): List<CharacterResult>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllCharacters(listCharacters: List<CharacterResult>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateCharacterFavorite(character: CharacterResult)
}