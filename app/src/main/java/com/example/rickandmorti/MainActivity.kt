package com.example.rickandmorti

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorti.databinding.ActivityMainBinding
import com.example.rickandmorti.ui.CharacterAdapter
import com.example.rickandmorti.ui.CharacterDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = CharacterAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCharacters.layoutManager = LinearLayoutManager(this)
        binding.rvCharacters.adapter = adapter

        viewModel.getCharacters().observe(this) { data ->
            adapter.submitData(lifecycle, data)
        }

        adapter.setOnItemClickListener { character ->
            val intent = Intent(this, CharacterDetailsActivity::class.java).apply {
                putExtra("characterId", character.id)
            }
            startActivity(intent)
        }
    }
}