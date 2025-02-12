package com.example.teste.ui.moviesScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.teste.R
import com.example.teste.databinding.MoviesCellBinding
import com.example.teste.domain.models.MoviesModel

class MovieRecyclerViewAdapter :
    PagingDataAdapter<MoviesModel, MovieRecyclerViewAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = MoviesCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

     inner class MovieViewHolder(private val binding: MoviesCellBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MoviesModel) {
            binding.movieName.text = movie.title
            binding.movieImage.load("https://image.tmdb.org/t/p/w300${movie.poster_path}") {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_foreground)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviesModel>() {
            override fun areItemsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MoviesModel, newItem: MoviesModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}