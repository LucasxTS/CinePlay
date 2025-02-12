package com.example.teste.ui.moviesScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = MovieRecyclerViewAdapter()
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            moviesViewModel.movies.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}