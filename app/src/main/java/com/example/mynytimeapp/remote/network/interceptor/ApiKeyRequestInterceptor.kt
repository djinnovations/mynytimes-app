package com.example.mynytimeapp.remote.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api-key", "AxAqMbDHdLFCAl06o1YqogGS3A6QygLL")
            .build()
        request = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }


}