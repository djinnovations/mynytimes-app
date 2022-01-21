package com.example.mynytimeapp.home

import android.os.Parcelable
import com.example.mynytimeapp.home.model.PopularListResponse
import kotlinx.android.parcel.Parcelize

sealed class HomeState : Parcelable {

    @Parcelize
    object IdleState : HomeState(), Parcelable

    @Parcelize
    object InitState : HomeState(), Parcelable

    @Parcelize
    data class LoadingState(val isLoading:Boolean) : HomeState(), Parcelable

    @Parcelize
    data class FailedState(
        val message: String? = null
    ) : HomeState(), Parcelable

    @Parcelize
    data class StartWebViewState(
        val url: String? = null,
        val title: String? = ""
    ) : HomeState(), Parcelable

    sealed class HomeFragmentState : HomeState() {
        @Parcelize
        object InitState : HomeFragmentState(), Parcelable

        @Parcelize
        object FetchNyPopularListState : HomeFragmentState(), Parcelable

        @Parcelize
        data class FetchNyPopularListResponseState(
            val message: String? = null,
            val data: List<PopularListResponse.ArticleItem>? = null,
            val code: Int? = null,
            val isSuccess: Boolean
        ) : HomeFragmentState(), Parcelable

        @Parcelize
        data class ArticleItemClickState(
            val data: PopularListResponse.ArticleItem
        ) : HomeFragmentState(), Parcelable

        @Parcelize
        data class InitLoadFailedState(
            val message: String? = null
        ) : HomeState(), Parcelable
    }
}