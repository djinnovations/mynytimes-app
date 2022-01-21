package com.example.mynytimeapp.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    private val mBaseDisposables: CompositeDisposable = CompositeDisposable()

    fun addToDispose(disposable: Disposable){
        mBaseDisposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        mBaseDisposables.clear()
    }
}