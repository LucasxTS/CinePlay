package com.example.teste.ui.moviesScreen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teste.R
import com.example.teste.databinding.MoviesFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoviesFragmentView: Fragment() {

    private lateinit var binding: MoviesFragmentBinding
    private val moviesViewModel: MoviesFragmentViewModel by viewModel()
    private lateinit var adapter: MovieRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        setupRecyclerView()
        observeFavorites()
        navigateToFavoritesMoviesView()
        setupSearchBar()
        setupAdultSpinner()
        setupVoteAverage()
    }

    private fun setupVoteAverage() {
        val ratingOptions = arrayOf("All", "High", "Medium", "Low")
        val ratingAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, ratingOptions)
        ratingAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.voteSpinner.adapter = ratingAdapter

        binding.voteSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = when (position) {
                    1 -> "High"
                    2 -> "Medium"
                    3 -> "Low"
                    else -> null
                }
                moviesViewModel.setRatingFilter(selected)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupAdultSpinner() {
        val adultOptions = arrayOf("All", "Adult", "General")
        val adultAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, adultOptions)
        adultAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.adultSpinner.adapter = adultAdapter

        binding.adultSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = when (position) {
                    1 -> true
                    2 -> false
                    else -> null
                }
                moviesViewModel.setAdultFilter(selected)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSearchBar() {
        binding.searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)?.apply {
            setTextColor(Color.WHITE)
            setHintTextColor(Color.WHITE)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                moviesViewModel.setSearchQuery(newText.orEmpty())
                return true
            }
        })
    }

    private fun navigateToFavoritesMoviesView() {
        binding.favoriteIconToNavigate.setOnClickListener {
            val action = MoviesFragmentViewDirections.fromMoviesFragmentToFavoritesMovies()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = MovieRecyclerViewAdapter(
            onFavoriteClick = { movie ->
                moviesViewModel.toggleToFavorite(movie)
            },
            isFavorite = { movieId ->
                moviesViewModel.favorites.value.contains(movieId)
            },
            onMovieClicked = { movie ->
                val action = MoviesFragmentViewDirections.fromMoviesFragmentToDescriptionMoviesFragment(movie)
                findNavController().navigate(action)
            }
        )
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            moviesViewModel.moviesFiltered.collectLatest { pagingData ->
                adapter.submitData(pagingData)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun observeFavorites() {
        lifecycleScope.launch {
            moviesViewModel.favorites.collectLatest { pagingData ->
                adapter.notifyDataSetChanged()
            }
        }
    }
}