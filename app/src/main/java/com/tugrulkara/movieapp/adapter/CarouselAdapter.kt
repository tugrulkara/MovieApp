package com.tugrulkara.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tugrulkara.movieapp.data.model.MoviesResult
import com.tugrulkara.movieapp.databinding.ItemCarouselBinding
import com.tugrulkara.movieapp.util.Util.BACKDROP_URL
import com.tugrulkara.movieapp.util.downloadFromUrl
import com.tugrulkara.movieapp.util.placeholderProgressBar
import com.tugrulkara.movieapp.view.HomeFragmentDirections
import javax.inject.Inject

class CarouselAdapter @Inject constructor():RecyclerView.Adapter<CarouselAdapter.CarouselHolder>() {

    class CarouselHolder(val itembinding:ItemCarouselBinding):RecyclerView.ViewHolder(itembinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselHolder {
        val itembinding=ItemCarouselBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarouselHolder(itembinding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: CarouselHolder, position: Int) {
        holder.itembinding.carouselText.text= movieList[position].title
        holder.itembinding.movieOverview.text= movieList[position].overview
        holder.itembinding.carouselIv.downloadFromUrl(
            BACKDROP_URL+ movieList[position].backdropPath,
            placeholderProgressBar(holder.itemView.context)
        )

        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movieList[position])
            Navigation.findNavController(it).navigate(action)
        }
    }

    private val diffutil = object : DiffUtil.ItemCallback<MoviesResult>(){
        override fun areItemsTheSame(oldItem: MoviesResult, newItem: MoviesResult): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: MoviesResult, newItem: MoviesResult): Boolean {
            return oldItem==newItem
        }

    }

    private val recyclerListDiffer= AsyncListDiffer(this,diffutil)

    var movieList: List<MoviesResult>
        get()= recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)

}