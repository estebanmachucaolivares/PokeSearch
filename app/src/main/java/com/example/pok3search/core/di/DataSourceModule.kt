package com.example.pok3search.core.di

import com.example.pok3search.pokedex.data.database.LocalDataSourceImpl
import com.example.pok3search.pokedex.data.network.PokemonService
import com.example.pok3search.pokedex.data.network.RemoteDataSourceImpl
import com.example.pok3search.pokedex.domain.datasource.LocalDataSource
import com.example.pok3search.pokedex.domain.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(): LocalDataSource {
        return LocalDataSourceImpl()
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: PokemonService): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }
}