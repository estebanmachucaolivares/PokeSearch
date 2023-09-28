package com.example.pok3search.pokedex.data.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.pok3search.pokedex.data.database.entities.RegionEntity
import com.example.pok3search.pokedex.data.database.entities.RegionWithPokemon
import kotlinx.coroutines.flow.Flow
import com.example.pok3search.pokedex.domain.Result

@Dao
interface RegionDao {
    @Query("SELECT * FROM region")
    fun getAllRegions(): List<RegionEntity>

    @Insert
    suspend fun insertRegion(region: RegionEntity): Long

    @Transaction
    @Query("SELECT * FROM region")
    fun getRegionsWithPokemon(): Flow<List<RegionWithPokemon>>
}