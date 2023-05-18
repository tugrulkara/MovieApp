package com.tugrulkara.movieapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.tugrulkara.movieapp.R
import com.tugrulkara.movieapp.adapter.CarouselAdapter
import com.tugrulkara.movieapp.adapter.HomeFragmentAdapter
import com.tugrulkara.movieapp.databinding.FragmentHomeBinding
import com.tugrulkara.movieapp.util.Status
import com.tugrulkara.movieapp.util.autoScroll
import com.tugrulkara.movieapp.viewmodel.HomeFragmentViewModel
import javax.inject.Inject

class HomeFragment @Inject constructor(private val homeFragmentAdapter: HomeFragmentAdapter,
                                       private val carouselAdapter: CarouselAdapter): Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itembinding=FragmentHomeBinding.bind(view)
        _binding=itembinding
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel=ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        viewModel.getNowPlaying()
        viewModel.getMovies()

        initRecycler()
        subscribeToObserver()

    }

    private fun initRecycler(){
        binding.homeRv.layoutManager=GridLayoutManager(requireContext(),2)
        binding.homeRv.adapter=homeFragmentAdapter
    }

    private fun subscribeToObserver(){

        viewModel.movieList.observe(viewLifecycleOwner, Observer {

            when(it.status){

                Status.SUCCESS->{
                    val movies = it.data?.movies
                    homeFragmentAdapter.movieList=movies ?: listOf()
                    binding.progressHome.visibility=View.GONE
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                    binding.progressHome.visibility=View.GONE
                }
                Status.LOADING->{
                    binding.progressHome.visibility=View.VISIBLE
                }

            }
        })

        viewModel.nowPlayingList.observe(viewLifecycleOwner, Observer {
            when(it.status){

                Status.SUCCESS->{
                    val movies = it.data?.movies
                    carouselAdapter.movieList=movies ?: listOf()
                    binding.viewPager.adapter = carouselAdapter
                    TabLayoutMediator(binding.tabDots, binding.viewPager,true) { tab, position -> }.attach()
                    binding.viewPager.autoScroll(10000)
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING->{

                }

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}