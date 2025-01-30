package com.example.rickandmorti.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorti.MainViewModel
import com.example.rickandmorti.databinding.FragmentMainBinding
import com.example.rickandmorti.ui.CharacterAdapter

class MainFragment : Fragment() {
    private val adapter = CharacterAdapter()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        viewModel.getCharacters()
        viewModel.charactersData.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }
    }

    private fun setupRecycler() = with(binding) {
        rvCharacters.adapter = adapter
        rvCharacters.layoutManager = LinearLayoutManager(
            requireContext(),
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            true
        )
    }
}