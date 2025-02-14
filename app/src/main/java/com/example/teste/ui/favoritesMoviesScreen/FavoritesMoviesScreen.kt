package com.example.teste.ui.favoritesMoviesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.teste.databinding.FavoriteMoviesFragmentBinding
import com.example.teste.ui.moviesScreen.MovieRecyclerViewAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesMoviesScreen(): Fragment() {
    private lateinit var binding: FavoriteMoviesFragmentBinding
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel by viewModel()
    private lateinit var adapter: MovieRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteMoviesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setupRecyclerView()
        observeFavorites()
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.favoriteMovies
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = MovieRecyclerViewAdapter(
            onMovieClicked = { movie ->
                val action = FavoritesMoviesScreenDirections.fromFavoriteMoviesToDescriptionFragment(movie)
                findNavController().navigate(action)
            },
            onFavoriteClick = { movie ->
                favoriteMoviesViewModel.toggleToFavorite(movie)
            },
            isFavorite = { movieId ->
                favoriteMoviesViewModel.favorites.value.contains(movieId)
            }
        )
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            favoriteMoviesViewModel.movies.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            favoriteMoviesViewModel.favorites.collectLatest {
                adapter.notifyDataSetChanged()
            }
        }
    }
}