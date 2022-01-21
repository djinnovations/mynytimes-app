package com.example.mynytimeapp.di

import com.example.mynytimeapp.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class
    ]
)
interface DataComponent {
    fun inject(viewModel: HomeViewModel)
}