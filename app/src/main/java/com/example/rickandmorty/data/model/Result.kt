package com.example.rickandmorty.data.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "characters")
@Parcelize
data class Result(
    @SerializedName("gender")
    var gender: String = "",
    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,
    @SerializedName("image")
    var image: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("species")
    var species: String = "",
    @SerializedName("status")
    var status: String = "",
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
): Parcelable