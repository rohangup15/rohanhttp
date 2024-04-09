package com.rohan.rohannetworking.di

import com.rohan.rohanhttp.CustomNetworkManager
import com.rohan.rohanhttp.NetworkClientType
import com.rohan.rohannetworking.datasources.MovieRemoteDataSource
import com.rohan.rohannetworking.datasources.MovieRemoteDataSourceImpl
import com.rohan.rohannetworking.repositories.MovieRepository
import com.rohan.rohannetworking.repositories.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesMovieRemoteDataSource(
        manager: CustomNetworkManager
    ): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(manager)
    }

    @Singleton
    @Provides
    fun providesMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource)
    }


    @Singleton
    @Provides
    fun getCustomNetworkManager(): CustomNetworkManager = CustomNetworkManager(NetworkClientType.OKHTTP_CLIENT)
}