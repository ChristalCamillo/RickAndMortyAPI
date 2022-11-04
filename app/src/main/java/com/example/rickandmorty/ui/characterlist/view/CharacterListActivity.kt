package com.example.rickandmorty.ui.characterlist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.CHARACTER_KEY
import com.example.rickandmorty.data.model.CharacterResult
import com.example.rickandmorty.databinding.ActivityCharacterListBinding
import com.example.rickandmorty.ui.characterdetail.view.CharacterDetailActivity
import com.example.rickandmorty.ui.characterfavorite.view.CharacterFavoriteActivity
import com.example.rickandmorty.ui.characterlist.viewmodel.CharacterListViewModel
import com.example.rickandmorty.ui.viewstate.ViewState

class CharactersListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterListBinding

    private val viewModel: CharacterListViewModel by lazy {
        ViewModelProvider(this)[CharacterListViewModel::class.java]
    }

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(mutableListOf(), this::goToCharacterDetail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favButton.setOnClickListener {
            goToCharacterFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        initObserver()
        viewModel.getAllCharactersNetwork()
        showRecyclerView()
    }

    private fun showRecyclerView(){
        binding.rvCharactersList.adapter = characterAdapter
        binding.rvCharactersList.layoutManager = GridLayoutManager(this, 2)
    }

    private fun initObserver(){
        viewModel.characterListState.observe(this){
            when(it){
                is ViewState.Success -> {
                    characterAdapter.updateCharacterList(it.data as MutableList<CharacterResult>)
                }
                is ViewState.Error -> {
                    Toast.makeText(this, "${it.throwable.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun goToCharacterDetail(character: CharacterResult){
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra(CHARACTER_KEY,character)
        startActivity(intent)
    }

    private fun goToCharacterFavorite(){
        val intent = Intent(this, CharacterFavoriteActivity::class.java)
        startActivity(intent)
    }

}