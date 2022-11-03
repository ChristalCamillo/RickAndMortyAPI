package com.example.rickandmorty.data.model


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("results")
    var results: List<Result> = listOf()
)