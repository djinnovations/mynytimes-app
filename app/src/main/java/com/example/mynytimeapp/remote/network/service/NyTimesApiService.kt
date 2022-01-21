package com.example.mynytimeapp.remote.network.service

import com.example.mynytimeapp.home.model.PopularListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NyTimesApiService {

    @GET("svc/mostpopular/v2/mostviewed/{section}/{period}.json")
    fun getMostPopularSection(
        @Path("section") section: String,
        @Path("period") period: String
    ): Call<PopularListResponse>

//    http://api.nytimes.com/svc/mostpopular/v2/mostviewed/{section}/{period}.json?api-key=sample-key

//    http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?api-key=sample-key

//    Bohr1234$
}
