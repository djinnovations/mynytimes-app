package com.example.mynytimeapp.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import com.example.mynytimeapp.home.HomeState.*
import androidx.lifecycle.viewModelScope
import com.example.mynytimeapp.base.BaseViewModel
import com.example.mynytimeapp.MyApp
import com.example.mynytimeapp.repo.NyTimesRepo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var mNyTimesRepo: NyTimesRepo

    init {
        MyApp.dataComponent().inject(this)
    }

    val mStateObservable: MutableLiveData<HomeState> by lazy {
        MutableLiveData<HomeState>()
    }

    private var mState: HomeState = IdleState
        set(value) {
            field = value
            publishState(value)
        }

    private fun publishState(state: HomeState) {
        //always publish on UI thread
        viewModelScope.launch(Dispatchers.Main) {
            mStateObservable.setValue(state)
        }
    }

    //not to be called from within
    fun nextState(state: HomeState) {
        when (state) {
            HomeFragmentState.FetchNyPopularListState -> {
                mState = LoadingState(true)
                getNyPopularList("all-sections", "7")
            }

            is HomeFragmentState.ArticleItemClickState -> {
                mState = StartWebViewState(
                    url = state.data.articleLink,
                    title = state.data.title
                )
            }

            else -> {
                mState = state
            }
        }
    }

    private fun getNyPopularList(section: String, period: String) {
        mNyTimesRepo.getMostPopularList(
            section, period
        ).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe({
                it?.also {
                    if (it.success) {
                        it.response.itemList?.also { list ->
                            mState = HomeFragmentState.FetchNyPopularListResponseState(
                                isSuccess = true,
                                data = list
                            )
                        } ?: kotlin.run {
                            mState = HomeFragmentState.FetchNyPopularListResponseState(
                                isSuccess = false
                            )
                        }
                    } else {
                        mState = HomeFragmentState.FetchNyPopularListResponseState(
                            isSuccess = false,
                            message = it.networkError.message
                        )
                    }
                } ?: kotlin.run {
                    mState = HomeFragmentState.FetchNyPopularListResponseState(
                        isSuccess = false
                    )
                }
            }, {
                mState = HomeFragmentState.FetchNyPopularListResponseState(
                    isSuccess = false,
                    message = it.localizedMessage ?: it?.message
                )
            }).apply {
                addToDispose(this)
            }
    }
}