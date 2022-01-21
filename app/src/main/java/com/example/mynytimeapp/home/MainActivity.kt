package com.example.mynytimeapp.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mynytimeapp.R
import com.example.mynytimeapp.base.BaseActivity
import com.example.mynytimeapp.createFactory
import com.example.mynytimeapp.databinding.ActivityMainBinding
import com.example.mynytimeapp.toast

class MainActivity : BaseActivity() {

    companion object {
        //use start function to fire activities
        @JvmStatic
        fun start(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private lateinit var mViewModel: HomeViewModel
    private lateinit var mViewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initVm()
        postEventsToViewModel(HomeState.InitState)
    }

    private fun initVm() {
        //use activity store for vm
        val factory = HomeViewModel(application).createFactory()
        mViewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)
        mViewModel.mStateObservable.observe(this, {
            updateView(it)
        })
    }

    private fun updateView(state: HomeState) {
        when (state) {
            HomeState.InitState -> {
                addFragment(HomeFragment.newInstance())
            }

            is HomeState.StartWebViewState -> {
                addFragment(MyWebViewFragment.newInstance(state.url, state.title),
                    true)
            }

            is HomeState.FailedState -> {
                //generic error
                val message = getString(R.string.generic_error)
                toast(message)
            }

            else -> {
                //do nothing
            }
        }


        }

    private fun postEventsToViewModel(state: HomeState){
        mViewModel.nextState(state)
    }
}