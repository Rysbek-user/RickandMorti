package com.example.rickandmorti.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorti.R
import com.example.rickandmorti.databinding.ItemCharacterBinding

class CharacterAdapter() : ListAdapter<com.example.rickandmorti.data.model.Character, CharacterAdapter.CharacterViewHolder>(diffUtil) {
    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(character: com.example.rickandmorti.data.model.Character) = with(binding) {
            characterName.text = character.name
            characterLocation.text = character.location?.name
            characterFirstSeen.text = character.origin?.name
            /*imgCharacter.load(character.image){
                crossfade(true)
            }*/
            Glide.with(imgCharacter).load(character.image).into(imgCharacter)
            characterStatus.text = character.status
            colorIndicator.setImageResource(
                when{
                    character.status?.contains("Dead") == true -> R.drawable.ic_circle_red
                    character.status?.contains("Alive") == true -> R.drawable.ic_circle_green
                    else -> R.drawable.ic_circle_gray
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<com.example.rickandmorti.data.model.Character>() {
            override fun areItemsTheSame(oldItem: com.example.rickandmorti.data.model.Character, newItem: com.example.rickandmorti.data.model.Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: com.example.rickandmorti.data.model.Character, newItem: com.example.rickandmorti.data.model.Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}