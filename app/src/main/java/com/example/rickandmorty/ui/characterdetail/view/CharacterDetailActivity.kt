package com.example.rickandmorty.ui.characterdetail.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.CHARACTER_KEY
import com.example.rickandmorty.R
import com.example.rickandmorty.data.model.CharacterResult
import com.example.rickandmorty.databinding.ActivityCharacterDetailBinding
import com.example.rickandmorty.ui.characterdetail.viewmodel.CharacterDetailViewModel
import com.squareup.picasso.Picasso

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private lateinit var character: CharacterResult

    private val viewModel: CharacterDetailViewModel by lazy {
        ViewModelProvider(this)[CharacterDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getData()

        binding.iconStar.setOnClickListener {
            character.isFavorite = !character.isFavorite
            updateFavoriteIconColor()
            viewModel.updateCharacterFavorite(character)
            showFavoriteUpdateToast()
        }
    }

    private fun getData(){

        val data = intent.getParcelableExtra<CharacterResult>(CHARACTER_KEY)

        if(data != null){
            character = data

            character.let {
                Picasso.get().load(it.image).into(binding.ivCharacterImageDetail)
                binding.tvCharacterName.text = "Nome: " + it.name
                binding.tvCharacterGender.text = "Gênero: " + it.gender
                binding.tvCharacterSpecies.text = "Espécie: " + it.species
                binding.tvCharacterStatus.text = "Status: " + it.status
                updateFavoriteIconColor()

                this.supportActionBar?.title = it.name
            }
        }else{
            Toast.makeText(this, "Não foi possível carregar as infos dos personagen", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateFavoriteIconColor(){
        binding.iconStar.setImageDrawable(
            ContextCompat.getDrawable(
                binding.root.context,
                if (character.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_disfavorite
            )
        )
    }

    private fun showFavoriteUpdateToast(){
        if(character.isFavorite){
            Toast.makeText(this, "${character.name} foi favoritado com sucesso!", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this, "${character.name} foi desfavoritado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}