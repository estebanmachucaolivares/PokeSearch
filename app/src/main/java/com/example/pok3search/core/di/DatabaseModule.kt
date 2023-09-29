package com.example.pok3search.core.di

import android.content.Context
import androidx.room.Room
import com.example.pok3search.pokedex.data.database.PokemonDatabase
import com.example.pok3search.pokedex.data.database.dao.PokemonDao
import com.example.pok3search.pokedex.data.database.dao.PokemonDescriptionDao
import com.example.pok3search.pokedex.data.database.dao.RegionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideRegionDao(pokemonDatabase: PokemonDatabase):RegionDao{
        return pokemonDatabase.regionDao()
    }

    @Provides
    fun pokemonRegionDao(pokemonDatabase: PokemonDatabase):PokemonDao{
        return pokemonDatabase.pokemonDao()
    }

    @Provides
    fun pokemonDescriptionDao(pokemonDatabase: PokemonDatabase):PokemonDescriptionDao{
        return pokemonDatabase.pokemonDescriptionDao()
    }

    @Singleton
    @Provides
    fun providePokemonDatabase(@ApplicationContext appContext:Context):PokemonDatabase{
        return Room.databaseBuilder(appContext, PokemonDatabase::class.java,"PokemonDatabas").build()
    }
}