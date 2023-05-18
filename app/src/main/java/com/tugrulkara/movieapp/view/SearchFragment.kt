package com.tugrulkara.movieapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tugrulkara.movieapp.R
import com.tugrulkara.movieapp.adapter.SearchFragmentAdapter
import com.tugrulkara.movieapp.databinding.FragmentSearchBinding
import com.tugrulkara.movieapp.util.Status
import com.tugrulkara.movieapp.viewmodel.SearchFragmentViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment @Inject constructor(private val searchFragmentAdapter: SearchFragmentAdapter):Fragment(R.layout.fragment_search) {

    private var _binding:FragmentSearchBinding ?= null
    val binding get() = _binding!!

    private lateinit var viewModel:SearchFragmentViewModel
    private var job : Job?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itembinding=FragmentSearchBinding.bind(view)
        _binding=itembinding

        viewModel=ViewModelProvider(requireActivity()).get(SearchFragmentViewModel::class.java)

        initListener()
        initRecycler()
        subscribeToObserver()
        onBackPressed()
    }

    fun initListener(){

        binding.searchText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchMovie(it.toString())
                    }
                }
            }

        }
    }

    fun initRecycler(){
        binding.searchRv.layoutManager=GridLayoutManager(requireContext(),2)
        binding.searchRv.adapter=searchFragmentAdapter
    }

    fun subscribeToObserver(){

        viewModel.movieList.observe(viewLifecycleOwner, Observer {

            when(it.status){
                Status.SUCCESS->{
                    val movie= it.data?.movies
                    searchFragmentAdapter.movieList=movie ?: listOf()
                    binding.searchProgress.visibility=View.GONE
                    binding.searchIcon.visibility=View.GONE
                }
                Status.ERROR->{
                    Toast.makeText(requireContext(),"Error", Toast.LENGTH_SHORT).show()
                    binding.searchProgress.visibility=View.GONE
                    binding.searchIcon.visibility=View.GONE
                }
                Status.LOADING->{
                    binding.searchProgress.visibility=View.VISIBLE
                    binding.searchIcon.visibility=View.GONE
                }
            }

        })

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