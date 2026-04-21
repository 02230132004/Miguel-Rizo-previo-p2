package com.example.seriesexplorer.ui.detail

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.seriesexplorer.databinding.FragmentShowDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowDetailFragment : Fragment() {

    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShowDetailViewModel by viewModels()
    private val args: ShowDetailFragmentArgs by navArgs()
    private lateinit var episodeAdapter: EpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUiState()
        viewModel.fetchShowDetail(args.showId)
    }

    private fun setupRecyclerView() {
        episodeAdapter = EpisodeAdapter()
        binding.rvEpisodes.adapter = episodeAdapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ShowDetailUiState.Loading -> {
                            binding.progressBarDetail.isVisible = true
                        }
                        is ShowDetailUiState.Success -> {
                            binding.progressBarDetail.isVisible = false
                            val show = state.show
                            binding.tvDetailName.text = show.name
                            binding.tvDetailSummary.text = Html.fromHtml(show.summary ?: "", Html.FROM_HTML_MODE_COMPACT)
                            
                            Glide.with(this@ShowDetailFragment)
                                .load(show.imageUrl)
                                .into(binding.ivDetailPoster)

                            if (show.countryFlag != null) {
                                Glide.with(this@ShowDetailFragment)
                                    .load(show.countryFlag)
                                    .into(binding.ivCountryFlag)
                            }
                            
                            episodeAdapter.submitList(state.episodes)
                        }
                        is ShowDetailUiState.Error -> {
                            binding.progressBarDetail.isVisible = false
                            // Show error message
                        }
                        is ShowDetailUiState.Empty -> {
                            binding.progressBarDetail.isVisible = false
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