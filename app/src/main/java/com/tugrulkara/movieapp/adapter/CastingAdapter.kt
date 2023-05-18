package com.tugrulkara.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tugrulkara.movieapp.data.model.CastingResult
import com.tugrulkara.movieapp.databinding.ItemCastBinding
import com.tugrulkara.movieapp.util.Util.POSTER_URL
import com.tugrulkara.movieapp.util.downloadFromUrl
import com.tugrulkara.movieapp.util.placeholderProgressBar
import javax.inject.Inject

class CastingAdapter @Inject constructor():RecyclerView.Adapter<CastingAdapter.CastHolder>() {
    class CastHolder(val itembinding:ItemCastBinding):RecyclerView.ViewHolder(itembinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val itembinding=ItemCastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CastHolder(itembinding)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        holder.itembinding.castIv.downloadFromUrl(POSTER_URL+ castList[position].profilePath,
            placeholderProgressBar(holder.itemView.context)
        )
    }

    private val diffutil = object : DiffUtil.ItemCallback<CastingResult>(){
        override fun areItemsTheSame(oldItem: CastingResult, newItem: CastingResult): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: CastingResult, newItem: CastingResult): Boolean {
            return oldItem==newItem
        }

    }

    private val recyclerListDiffer= AsyncListDiffer(this,diffutil)

    var castList: List<CastingResult>
        get()= recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)
}