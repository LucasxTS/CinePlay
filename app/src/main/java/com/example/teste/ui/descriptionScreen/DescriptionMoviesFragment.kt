package com.example.teste.ui.descriptionScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.ViewSizeResolver
import com.example.teste.R
import com.example.teste.databinding.DescriptionMoviesFragmentBinding

class DescriptionMoviesFragment(): Fragment() {
    private lateinit var binding: DescriptionMoviesFragmentBinding
    private val args: DescriptionMoviesFragmentArgs by navArgs()
    private val url = "https://image.tmdb.org/t/p/w500"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DescriptionMoviesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMovieOnView()
    }

    private fun setupMovieOnView() {
        binding.titleTextView.text = args.movie.title
        binding.overviewTextView.text = args.movie.overview
        binding.releaseDateTextView.text = getString(R.string.releaseDateDescription, args.movie.release_date)
        binding.voteAverageTextView.text = args.movie.vote_average.formatToOneDecimal()
        binding.backdropImageView.load("$url${args.movie.backdrop_path}") {
            crossfade(true)
            size(ViewSizeResolver(binding.backdropImageView))
        }
    }

    private fun Double.formatToOneDecimal(): String {
        return String.format("%.1f", this)
    }
}