package com.tugrulkara.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tugrulkara.movieapp.data.roomdb.MovieEntity
import com.tugrulkara.movieapp.databinding.ItemFavMovieBinding
import com.tugrulkara.movieapp.util.DataMapper
import com.tugrulkara.movieapp.util.Util
import com.tugrulkara.movieapp.util.downloadFromUrl
import com.tugrulkara.movieapp.util.placeholderProgressBar
import com.tugrulkara.movieapp.view.FavMovieFragmentDirections
import javax.inject.Inject

class FavMovieFragmentAdapter @Inject constructor():RecyclerView.Adapter<FavMovieFragmentAdapter.FavHolder>() {

    class FavHolder(val itembinding:ItemFavMovieBinding):RecyclerView.ViewHolder(itembinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHolder {
        val itembinding = ItemFavMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavHolder(itembinding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: FavHolder, position: Int) {
        holder.itembinding.favMovieTitleText.text= movieList[position].title
        holder.itembinding.movieDescription.text= movieList[position].releaseDate
        movieList[position].posterPath?.let {
            holder.itembinding.favPosterIv.downloadFromUrl(Util.POSTER_URL+it, placeholderProgressBar(holder.itemView.context))
        }
        holder.itemView.setOnClickListener {
            val movieResult= DataMapper.mapMovieEntityToMovieResult(movieList[position])
            val action = FavMovieFragmentDirections.actionFavMovieFragmentToDetailFragment(movieResult)
            Navigation.findNavController(it).navigate(action)
        }
    }

    private val diffutil = object : DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem==newItem
        }
        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem==newItem
        }
    }

    private val recyclerListDiffer= AsyncListDiffer(this,diffutil)

    var movieList: List<MovieEntity>
        get()= recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)
}
