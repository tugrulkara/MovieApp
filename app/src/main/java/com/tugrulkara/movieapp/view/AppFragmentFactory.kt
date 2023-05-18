package com.tugrulkara.movieapp.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.tugrulkara.movieapp.adapter.*
import javax.inject.Inject

class AppFragmentFactory @Inject constructor(
    private val homeFragmentAdapter: HomeFragmentAdapter
    ,private val favMovieFragmentAdapter: FavMovieFragmentAdapter
    ,private val searchFragmentAdapter: SearchFragmentAdapter
    ,private val movieVideosAdapter: MovieVideosAdapter
    ,private val carouselAdapter: CarouselAdapter
    ,private val castingAdapter: CastingAdapter): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            HomeFragment::class.java.name->HomeFragment(homeFragmentAdapter,carouselAdapter)
            FavMovieFragment::class.java.name->FavMovieFragment(favMovieFragmentAdapter)
            SearchFragment::class.java.name->SearchFragment(searchFragmentAdapter)
            DetailFragment::class.java.name->DetailFragment(movieVideosAdapter,castingAdapter)
            else ->super.instantiate(classLoader, className)
        }
    }
}