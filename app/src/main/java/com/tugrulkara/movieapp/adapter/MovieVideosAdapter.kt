package com.tugrulkara.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.tugrulkara.movieapp.data.model.VideosResult
import com.tugrulkara.movieapp.databinding.ItemVideosBinding
import javax.inject.Inject

class MovieVideosAdapter @Inject constructor():RecyclerView.Adapter<MovieVideosAdapter.VideoHolder>() {

    class VideoHolder(val itembinding:ItemVideosBinding):RecyclerView.ViewHolder(itembinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val itembinding=ItemVideosBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoHolder(itembinding)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val videoId=videoList[position].key
        holder.itembinding.itemYoutubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                videoId?.let {
                    youTubePlayer.cueVideo(it, 0f)
                }
            }
        })
    }

    private val diffutil = object : DiffUtil.ItemCallback<VideosResult>(){
        override fun areItemsTheSame(oldItem: VideosResult, newItem: VideosResult): Boolean {
            return oldItem==newItem
        }
        override fun areContentsTheSame(oldItem: VideosResult, newItem: VideosResult): Boolean {
            return oldItem==newItem
        }
    }

    private val recyclerListDiffer= AsyncListDiffer(this,diffutil)

    var videoList: List<VideosResult>
        get()= recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)
}