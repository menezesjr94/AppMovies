package com.example.appmovies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appmovies.common.util.Constants
import com.example.appmovies.common.util.formatDate
import com.example.appmovies.databinding.LayoutItemMovieBinding
import com.example.appmovies.model.MovieDetails

@SuppressLint("NotifyDataSetChanged")
class MoviesAdapter (private val onClick: (MovieDetails) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    inner class MoviesViewHolder(val binding: LayoutItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val moviesList = mutableListOf<MovieDetails>()

    fun updateMovies(newMovies: List<MovieDetails>) {
        moviesList.addAll(newMovies)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = moviesList[position]
        holder.binding.apply {
            tvTitle.text = movie.title
            tvDate.text = formatDate(movie.releaseDate ?: "")
            Glide.with(holder.itemView.context)
                .load(Constants.posterBaseUrl + movie.posterPath)
                .into(cardMovieImgPoster)
            cardMovie.setOnClickListener {
                onClick.invoke(movie)
            }
        }
    }

    override fun getItemCount(): Int = moviesList.size

    fun getMoviePosition(position: Int): MovieDetails {
        return moviesList[position]
    }
}