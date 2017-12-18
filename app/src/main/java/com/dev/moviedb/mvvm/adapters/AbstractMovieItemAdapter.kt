package com.dev.moviedb.mvvm.adapters

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO

/**
 * An abstract class that helps reusing behavior between adapters.
 *
 * Yamda 1.0.0.
 */
abstract class AbstractMovieItemAdapter<T : ViewHolder?>: RecyclerView.Adapter<T>(){

    var movies: ArrayList<MovieDTO>? = null

    fun addNewData(movies: ArrayList<MovieDTO>?){
        this.movies = movies
    }

    override fun getItemCount(): Int = if (movies == null) 0 else movies?.size!!

}