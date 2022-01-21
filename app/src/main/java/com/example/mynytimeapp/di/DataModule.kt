package com.example.mynytimeapp.di

import com.example.mynytimeapp.repo.NyTimesRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Singleton
    @Provides
    fun provideHomeRepositoryI(): NyTimesRepo {
        return NyTimesRepo()
    }
}