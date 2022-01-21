package com.example.mynytimeapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynytimeapp.R
import com.example.mynytimeapp.base.BaseFragment
import com.example.mynytimeapp.createFactory
import com.example.mynytimeapp.databinding.FragmentHomeBinding
import com.example.mynytimeapp.home.HomeState.HomeFragmentState.*
import com.example.mynytimeapp.home.adapter.NyTimesArticleAdapter
import com.example.mynytimeapp.home.model.PopularListResponse

class HomeFragment : BaseFragment(), NyTimesArticleAdapter.SelectionListener {

    private lateinit var mViewModel: HomeViewModel
    private lateinit var mViewBinding: FragmentHomeBinding
    private var mAdapter: NyTimesArticleAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVm()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postEventsToViewModel(InitState)
    }

    private fun initVm() {
        //use activity store for vm
        val factory = HomeViewModel(requireActivity().application).createFactory()
        mViewModel = ViewModelProvider(requireActivity(), factory)
            .get(HomeViewModel::class.java)
        mViewModel.mStateObservable.observe(this, {
            mViewBinding.state = it
            updateView(it)
        })
    }


    private fun updateView(state: HomeState) {
        when (state) {
            InitState -> {
                init()
            }

            is FetchNyPopularListResponseState -> {
                postEventsToViewModel(HomeState.LoadingState(false))
                if (state.isSuccess) {
                    state.data?.also {
                        mAdapter?.submitList(it)
                    }
                } else {
                    //unable to apply changes, try again later
                    val message = state.message ?: requireActivity().getString(
                        R.string.generic_error
                    )
                    postEventsToViewModel(InitLoadFailedState(message))
                }
            }

            else -> {
                //do nothing
            }
        }

    }

    private fun init() {
        initToolBar()
        initRecyclerView()
        postEventsToViewModel(FetchNyPopularListState)
    }

    private fun initToolBar() {
        mViewBinding.includeToolbar.apply {
            tvTitle.text = getString(R.string.title)
            backButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun initRecyclerView() {
        mViewBinding.rvPopularItems.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            mAdapter = NyTimesArticleAdapter(
                this@HomeFragment
            )
            adapter = mAdapter
        }
    }

    override fun onItemClick(position: Int, item: PopularListResponse.ArticleItem) {
        postEventsToViewModel(ArticleItemClickState(item))
    }

    private fun postEventsToViewModel(state: HomeState) {
        mViewModel.nextState(state)
    }

}