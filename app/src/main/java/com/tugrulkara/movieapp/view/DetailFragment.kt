package com.tugrulkara.movieapp.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.tugrulkara.movieapp.R
import com.tugrulkara.movieapp.adapter.CastingAdapter
import com.tugrulkara.movieapp.adapter.MovieVideosAdapter
import com.tugrulkara.movieapp.data.model.MoviesResult
import com.tugrulkara.movieapp.databinding.FragmentDetailBinding
import com.tugrulkara.movieapp.util.DataMapper
import com.tugrulkara.movieapp.util.Status
import com.tugrulkara.movieapp.util.Util.BACKDROP_URL
import com.tugrulkara.movieapp.util.Util.POSTER_URL
import com.tugrulkara.movieapp.util.downloadFromUrl
import com.tugrulkara.movieapp.util.placeholderProgressBar
import com.tugrulkara.movieapp.viewmodel.DetailFragmentViewModel
import javax.inject.Inject

class DetailFragment @Inject constructor(private val movieVideosAdapter: MovieVideosAdapter,
                                         private val castingAdapter: CastingAdapter):Fragment(R.layout.fragment_detail) {

    private var _binding : FragmentDetailBinding?=null
    private val binding get() = _binding!!

    private lateinit var viewModel:DetailFragmentViewModel
    private var isFav : Boolean? = null
    private var uuid:Int?=null

    private var movieInfo: MoviesResult? = null

    private val genres = arrayListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itembinding=FragmentDetailBinding.bind(view)
        _binding=itembinding

        viewModel=ViewModelProvider(requireActivity()).get(DetailFragmentViewModel::class.java)
        viewModel.getDataGenre()

        arguments?.let { movieInfo=DetailFragmentArgs.fromBundle(it).movies }

        initUI()
        initRecycler()
        initListener()
        subscribeToObserver()
        onBackPressed()
    }

    private fun subscribeToObserver(){
        uuid?.let {infoId->
            viewModel.getFavMovie(infoId).observe(viewLifecycleOwner, Observer {movieEntity->

                movieEntity?.let {
                    if(it.id==infoId){
                        isFav=true
                        binding.btnFav.setColorFilter(Color.YELLOW)
                        //Toast.makeText(requireContext(),"DB ON", Toast.LENGTH_SHORT).show()
                    }else{
                        isFav=false
                        binding.btnFav.setColorFilter(Color.WHITE)
                        //Toast.makeText(requireContext(),"DB OFF", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

        viewModel.genreList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    it.data?.genres?.forEach { genre ->
                        movieInfo?.genreIds?.forEach { id ->
                            if (id == genre.id) {
                                genre.name?.let {
                                    genres.add(it)
                                }
                            }
                        }
                    }
                    binding.genreText.text=genres.joinToString(",")
                    genres.clear()
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING->{
                    Toast.makeText(requireContext(),"Loading", Toast.LENGTH_SHORT).show()
                }
            }

        })

        uuid?.let {movieId->
            viewModel.getDataVideos(movieId)
            viewModel.videoList.observe(viewLifecycleOwner, Observer {

                when(it.status){
                    Status.SUCCESS->{
                        it.data?.results?.forEach {videosResult->
                            if (videosResult.type == "Trailer") {
                               initPlayer(videosResult.key.toString())
                            }
                            val videos = it.data.results
                            movieVideosAdapter.videoList=videos
                        }

                    }
                    Status.ERROR->{
                        Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING->{
                        Toast.makeText(requireContext(),"Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        uuid?.let {movieId->
            viewModel.getCast(movieId)
            viewModel.castList.observe(viewLifecycleOwner, Observer {

                when(it.status){
                    Status.SUCCESS->{
                        val castList=it.data?.cast
                        castingAdapter.castList=castList ?: listOf()
                    }
                    Status.ERROR->{
                        Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING->{

                    }
                }
            })
        }

    }

    private fun initUI(){
        movieInfo?.let {movie->
            (activity as MainActivity).supportActionBar?.title = movie.title
            movie.id.let {uuid = it }
            binding.ivBackdrop.downloadFromUrl(BACKDROP_URL+movie.backdropPath, placeholderProgressBar(requireContext()))
            binding.collapsingToolBar.title=movie.title
            binding.releaseDateText.text=movie.releaseDate
            binding.voteText.text=movie.voteAverage.toString()
            binding.languageText.text=movie.originalLanguage
            binding.overviewText.text=movie.overview
            binding.ivPoster.downloadFromUrl(POSTER_URL+movie.posterPath, placeholderProgressBar(requireContext()))
            binding.overVTitleText.text=movie.title
        }
    }

    private fun initListener(){
        binding.btnFav.setOnClickListener {
            movieInfo?.let {
                val movieEntity= DataMapper.mapMovieResultToMovieEntity(it)
                if (isFav==true){
                    movieEntity.id?.let {it1->
                        viewModel.deleteMovie(it1)
                        Toast.makeText(requireContext(),"Fav Deleted", Toast.LENGTH_SHORT).show()
                    }
                    binding.btnFav.setColorFilter(Color.WHITE)
                    isFav=false

                }else{
                    viewModel.insertMovie(movieEntity)
                    Toast.makeText(requireContext(),"Fav Added", Toast.LENGTH_SHORT).show()
                    binding.btnFav.setColorFilter(Color.YELLOW)
                    isFav=true
                }
            }
        }
    }


    fun initPlayer(videoId:String){
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }

    fun initRecycler(){
        binding.videoRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.videoRv.adapter=movieVideosAdapter

        binding.castRv.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.castRv.adapter=castingAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBackPressed(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}