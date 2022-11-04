package com.example.rickandmorty.ui.characterfavorite.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.CHARACTER_KEY
import com.example.rickandmorty.data.model.CharacterResult
import com.example.rickandmorty.databinding.ActivityCharacterFavoriteBinding
import com.example.rickandmorty.ui.characterdetail.view.CharacterDetailActivity
import com.example.rickandmorty.ui.characterfavorite.viewmodel.CharacterFavoriteViewModel

class CharacterFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterFavoriteBinding

    private val viewModel: CharacterFavoriteViewModel by lazy {
        ViewModelProvider(this)[CharacterFavoriteViewModel::class.java]
    }

    private val favoritedCharactersAdapter: CharacterFavoriteAdapter by lazy {
        CharacterFavoriteAdapter(mutableListOf(), this::goToCharacterInfo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favoritos"
    }

    override fun onResume() {
        super.onResume()
        initObserver()
        viewModel.getAllCharactersFavorited()
        showRecyclerView()
    }

    private fun initObserver(){
        viewModel.characterFavoriteState.observe(this){
            when(it){
                is com.example.rickandmorty.ui.viewstate.ViewState.Success -> {
                    favoritedCharactersAdapter.updateFavoritedCharacterList(it.data as MutableList<CharacterResult>)
                }
                is com.example.rickandmorty.ui.viewstate.ViewState.Error -> {
                    Toast.makeText(this, "${it.throwable.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showRecyclerView(){
        binding.rvFavotiredCharactersList.adapter = favoritedCharactersAdapter
        binding.rvFavotiredCharactersList.layoutManager = GridLayoutManager(this,2)
    }

    private fun goToCharacterInfo(character: CharacterResult){
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra(CHARACTER_KEY,character)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}