package com.example.pok3search.pokedex.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.pok3search.pokedex.domain.model.PokemonAbility


@Entity(tableName = "pokemon_ability"
    ,indices = [Index(value = ["name", "pokemonId"], unique = true)])
data class PokemonAbilityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val pokemonId: Long
)

fun PokemonAbility.toEntity() = PokemonAbilityEntity(name = name, description =  description, pokemonId = pokemonId.toLong())
