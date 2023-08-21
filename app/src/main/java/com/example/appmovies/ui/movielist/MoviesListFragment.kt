package com.example.appmovies.ui.movielist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmovies.adapter.MoviesAdapter
import com.example.appmovies.common.core.State
import com.example.appmovies.common.extensions.hide
import com.example.appmovies.common.extensions.show
import com.example.appmovies.databinding.FragmentMoviesListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var moviesAdapter: MoviesAdapter

    private val viewModel: MoviesListViewModel by viewModels()
    private val currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeMovies()
    }

    private fun observeMovies() {
        lifecycleScope.launch {
            viewModel.movies.collect { result ->
                when (result) {
                    is State.Success -> {
                        result.data?.let { movies ->
                            moviesAdapter.updateMovies(movies.results)
                            binding.progressCircular.hide()
                        }
                    }
                    is State.Error -> {
                        binding.progressCircular.hide()
                        binding.errorLayout.root.show()
                        binding.errorLayout.btnRetry.setOnClickListener {
                            viewModel.fetchMovies(currentPage)
                        }
                    }
                    is State.Loading -> {
                        binding.progressCircular.show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        moviesAdapter = onClick()

        binding.rvMovies.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(addOnScrollListener())
        }
    }

    private fun addOnScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    if (!viewModel.isLoadingNextPage) {
                        viewModel.nextPage()
                    }
                }
            }
        }
    }


    private fun onClick() = MoviesAdapter { movie ->
        val action =
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)
    }
}