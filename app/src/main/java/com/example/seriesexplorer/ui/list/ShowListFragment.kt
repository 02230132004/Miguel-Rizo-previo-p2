package com.example.seriesexplorer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.seriesexplorer.databinding.FragmentShowListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowListFragment : Fragment() {

    private var _binding: FragmentShowListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShowListViewModel by viewModels()
    private lateinit var adapter: ShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        observeUiState()
    }

    private fun setupRecyclerView() {
        adapter = ShowAdapter { show ->
            val action = ShowListFragmentDirections.actionShowListFragmentToShowDetailFragment(show.id)
            findNavController().navigate(action)
        }
        binding.rvShows.adapter = adapter

        binding.rvShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchShows(isNextPage = true)
                }
            }
        })
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.swipeRefresh.isRefreshing = false
                    when (state) {
                        is ShowListUiState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.tvError.isVisible = false
                            binding.tvEmpty.isVisible = false
                        }
                        is ShowListUiState.Success -> {
                            binding.progressBar.isVisible = false
                            binding.tvError.isVisible = false
                            binding.tvEmpty.isVisible = false
                            adapter.submitList(state.data)
                        }
                        is ShowListUiState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.tvError.isVisible = true
                            binding.tvError.text = state.message
                            binding.tvEmpty.isVisible = false
                        }
                        is ShowListUiState.Empty -> {
                            binding.progressBar.isVisible = false
                            binding.tvError.isVisible = false
                            binding.tvEmpty.isVisible = true
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}