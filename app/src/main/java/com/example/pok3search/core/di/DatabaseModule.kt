package com.example.pok3search.core.di

import android.content.Context
import androidx.room.Room
import com.example.pok3search.pokedex.data.database.PokemonDatabase
import com.example.pok3search.pokedex.data.database.dao.*
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
    fun providePokemonDao(pokemonDatabase: PokemonDatabase):PokemonDao{
        return pokemonDatabase.pokemonDao()
    }

    @Provides
    fun providePokemonDescriptionDao(pokemonDatabase: PokemonDatabase):PokemonDescriptionDao{
        return pokemonDatabase.pokemonDescriptionDao()
    }

    @Provides
    fun provideTypeDao(pokemonDatabase: PokemonDatabase):TypeDao{
        return pokemonDatabase.typeDao()
    }

    @Provides
    fun providePokemonEvolutionDao(pokemonDatabase: PokemonDatabase):PokemonEvolutionDao{
        return pokemonDatabase.pokemonEvolutionDao()
    }

    @Singleton
    @Provides
    fun providePokemonDatabase(@ApplicationContext appContext:Context):PokemonDatabase{
        return Room.databaseBuilder(appContext, PokemonDatabase::class.java,"PokemonDatabas").build()
    }
}