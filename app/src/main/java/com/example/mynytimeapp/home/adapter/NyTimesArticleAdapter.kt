package com.example.mynytimeapp.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynytimeapp.databinding.ItemPopularTimesNyBinding
import com.example.mynytimeapp.home.model.PopularListResponse
import com.squareup.picasso.Picasso

class NyTimesArticleAdapter(val mSelectionListener: SelectionListener?= null) : RecyclerView.Adapter<NyTimesArticleAdapter.ItemViewHolder>() {

    interface SelectionListener {
        fun onItemClick(position: Int, item: PopularListResponse.ArticleItem)
    }

    private var mDataList = arrayListOf<PopularListResponse.ArticleItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemLayoutBinding = ItemPopularTimesNyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemLayoutBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mDataList[position])
    }

    inner class ItemViewHolder(
        private val itemBinding: ItemPopularTimesNyBinding
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bind(item: PopularListResponse.ArticleItem) {
            itemBinding.apply {
                tvTitle.text = item.title
                tvDesc.text = item.author
                tvDate.text = item.published
                val canCheckImage = item.mediaList?.size?: 0 > 0
                        && item.mediaList?.size?:0 > 0
                if (canCheckImage) {
                    item.mediaList?.get(0)?.metaDataList?.get(0)?.imageUrl?.also {
                        Picasso.get()
                            .load(it)
                            .into(ivView)
                    }
                }
                root.setOnClickListener(this@ItemViewHolder)
            }
        }

        override fun onClick(view: View) {
            mSelectionListener?.onItemClick(adapterPosition, mDataList[adapterPosition])
        }
    }

    override fun getItemCount(): Int = mDataList.size

    fun submitList(dataList: List<PopularListResponse.ArticleItem>){
        mDataList.apply {
            clear()
            addAll(dataList)
        }
        notifyDataSetChanged()
    }
}