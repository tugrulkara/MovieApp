package com.tugrulkara.movieapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tugrulkara.movieapp.R
import com.tugrulkara.movieapp.adapter.FavMovieFragmentAdapter
import com.tugrulkara.movieapp.databinding.FragmentFavMovieBinding
import com.tugrulkara.movieapp.viewmodel.FavMovieFragmentViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavMovieFragment @Inject constructor(private val favMovieFragmentAdapter: FavMovieFragmentAdapter):Fragment(R.layout.fragment_fav_movie) {

    private var _binding : FragmentFavMovieBinding?=null
    private val binding get() = _binding!!

    private lateinit var viewModel:FavMovieFragmentViewModel

    private val swipeCallback= object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedMovie= favMovieFragmentAdapter.movieList.get(layoutPosition)
            selectedMovie.id?.let { viewModel.deleteFavMovie(it) }
            Toast.makeText(requireContext(),"Fav Deleted", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itembinding=FragmentFavMovieBinding.bind(view)
        _binding=itembinding

        viewModel=ViewModelProvider(requireActivity()).get(FavMovieFragmentViewModel::class.java)

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.favRv)

        initRecycler()
        subscribeToObserver()
    }

    private fun initRecycler(){
        binding.favRv.adapter=favMovieFragmentAdapter
        binding.favRv.layoutManager=LinearLayoutManager(requireContext())
    }

    private fun subscribeToObserver(){

        lifecycleScope.launch{
            viewModel.movies.observe(viewLifecycleOwner, Observer {

                favMovieFragmentAdapter.movieList=it
                if (it.isNotEmpty()){
                    binding.favIcon.visibility=View.GONE
                }else{
                    binding.favIcon.visibility=View.VISIBLE
                }

            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}