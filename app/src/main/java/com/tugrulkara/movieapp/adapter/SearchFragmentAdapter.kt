package com.tugrulkara.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tugrulkara.movieapp.data.model.MoviesResult
import com.tugrulkara.movieapp.databinding.ItemMovieBinding
import com.tugrulkara.movieapp.util.Util
import com.tugrulkara.movieapp.util.downloadFromUrl
import com.tugrulkara.movieapp.util.placeholderProgressBar
import com.tugrulkara.movieapp.view.SearchFragmentDirections
import javax.inject.Inject

class SearchFragmentAdapter @Inject constructor():RecyclerView.Adapter<SearchFragmentAdapter.RowHolder>() {

    class RowHolder(val itembinding:ItemMovieBinding):RecyclerView.ViewHolder(itembinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itembinding=ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(itembinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itembinding.movieTitleText.text= movieList[position].title
        holder.itembinding.movieDateText.text= movieList[position].releaseDate
        movieList[position].posterPath?.let {
            holder.itembinding.posterIv.downloadFromUrl(Util.POSTER_URL +it, placeholderProgressBar(holder.itemView.context))
        }

        holder.itemView.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(movieList[position])
            Navigation.findNavController(it).navigate(action)
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<MoviesResult>() {
        override fun areItemsTheSame(oldItem: MoviesResult, newItem: MoviesResult): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MoviesResult, newItem: MoviesResult): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var movieList : List<MoviesResult>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

}