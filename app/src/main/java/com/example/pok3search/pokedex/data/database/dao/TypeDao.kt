package com.example.pok3search.pokedex.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pok3search.pokedex.data.database.PokemonDatabase
import com.example.pok3search.pokedex.data.database.entities.PokemonTypeCrossRef
import com.example.pok3search.pokedex.data.database.entities.TypeEntity
import com.example.pok3search.ui.theme.*

@Dao
interface TypeDao {

    @Query("SELECT * FROM type WHERE typeName = :typeName")
    suspend fun getTypes(typeName: String): TypeEntity?

    @Insert
    suspend fun insertType(type: TypeEntity): Long

    @Query("SELECT type.id, type.typeName FROM type INNER JOIN pokemon_type_crossref ON type.id = pokemon_type_crossref.typeId WHERE pokemon_type_crossref.pokemonId = :pokemonId")
    suspend fun getTypesForPokemon(pokemonId: Long): List<TypeEntity>

    @Insert
    suspend fun insertPokemonType(pokemonType: PokemonTypeCrossRef): Long

}




