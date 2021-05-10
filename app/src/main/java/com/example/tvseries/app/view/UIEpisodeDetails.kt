package com.example.tvseries.app.view

import android.graphics.Rect
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseries.R
import com.example.tvseries.app.adapters.EpisodesAdapter
import com.example.tvseries.app.viewmodel.VMShowDetails
import com.example.tvseries.databinding.FragmentUiepisodeDetailsBinding
import com.example.tvseries.domain.model.Episode
import com.example.tvseries.domain.model.Show

private const val ARG_EPISODE = "episode"

class UIEpisodeDetails : Fragment() {
    lateinit var binding: FragmentUiepisodeDetailsBinding
    private lateinit var episode: Episode

    companion object {
        fun newInstance(show: Show) =
            UIEpisodeDetails().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_EPISODE, show)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            episode = it.getParcelable(ARG_EPISODE)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_uiepisode_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        Glide.with(view.context)
            .load(episode.image?.original)
            .into(binding.ivWallpaper)

        binding.tvTitle.text = episode.name
        binding.tvSubtitle.text = String.format(resources.getString(R.string.episode_details_episode), episode.number.toString())
        binding.tvSeason.text = String.format(resources.getString(R.string.episode_details_season), episode.season.toString())
        binding.tvSummary.text = Html.fromHtml(episode.summary).toString()
    }

}