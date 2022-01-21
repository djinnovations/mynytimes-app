package com.example.mynytimeapp.di

import android.content.Context
import com.example.mynytimeapp.repo.NyTimesRepo
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class]
)
interface AppComponent {

    fun inject(nyTimesRepo: NyTimesRepo)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context) : Builder

        fun build() : AppComponent
    }
}