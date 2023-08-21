package com.example.appmovies.ui.favoritelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmovies.R
import com.example.appmovies.adapter.MoviesAdapter
import com.example.appmovies.common.core.State
import com.example.appmovies.common.extensions.hide
import com.example.appmovies.common.extensions.show
import com.example.appmovies.common.extensions.toast
import com.example.appmovies.databinding.FragmentFavoriteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteListFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteListBinding

    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: FavoriteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observerState()
    }

    private fun observerState() = lifecycleScope.launch {
        viewModel.favorites.collect { result ->
            when (result) {
                is State.Success -> {
                    result.data?.let {
                        binding.tvEmptyList.hide()
                        moviesAdapter.updateMovies(it.toList())
                    }
                }
                is State.Empty -> {
                    binding.tvEmptyList.show()
                }
                else -> {
                }
            }
        }
    }


    private fun setupRecyclerView() = with(binding) {
        moviesAdapter = onClick()

        rvFavoriteList.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context)
        }
        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(rvFavoriteList)
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movie = moviesAdapter.getMoviePosition(viewHolder.adapterPosition)
                viewModel.delete(movie).also {
                    toast(getString(R.string.message_delete_character))
                }
            }
        }
    }

    private fun onClick() = MoviesAdapter { movie ->
        val action =
            FavoriteListFragmentDirections.actionFavoriteListFragmentToMovieDetailFragment(movie)
        findNavController().navigate(action)

    }

}