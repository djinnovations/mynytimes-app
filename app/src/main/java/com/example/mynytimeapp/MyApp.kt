package com.example.mynytimeapp

import android.app.Application
import com.example.mynytimeapp.di.AppComponent
import com.example.mynytimeapp.di.DaggerAppComponent
import com.example.mynytimeapp.di.DaggerDataComponent
import com.example.mynytimeapp.di.DataComponent

class MyApp : Application() {

    private val appComponent : AppComponent by lazy {
        DaggerAppComponent.builder().context(this).build()
    }

    private val dataComponent : DataComponent by lazy {
        DaggerDataComponent.create()
    }

    init {
        instance = this
    }

    companion object{
        private lateinit var instance: MyApp

        fun getApp() : MyApp {
            return instance
        }

        fun appComponent(): AppComponent {
            return instance.appComponent
        }

        fun dataComponent(): DataComponent {
            return instance.dataComponent
        }
    }
}