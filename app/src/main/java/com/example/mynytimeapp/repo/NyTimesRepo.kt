package com.example.mynytimeapp.repo

import com.example.mynytimeapp.MyApp
import com.example.mynytimeapp.home.model.PopularListResponse
import com.example.mynytimeapp.remote.GenericNetworkResponseSingle
import com.example.mynytimeapp.remote.NetworkResponse
import com.example.mynytimeapp.remote.network.service.NyTimesApiService
import io.reactivex.Single
import javax.inject.Inject

class NyTimesRepo {

    @Inject
    lateinit var mApiService: NyTimesApiService

    init {
        MyApp.appComponent().inject(this)
    }

    fun getMostPopularList(section: String, period: String): Single<NetworkResponse<PopularListResponse>> {
        val apiCall = mApiService.getMostPopularSection(section, period)
        return GenericNetworkResponseSingle(apiCall, PopularListResponse::class)
    }

}