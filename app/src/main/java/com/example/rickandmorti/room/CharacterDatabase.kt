package com.example.rickandmorti.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 4)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharacterDao
}