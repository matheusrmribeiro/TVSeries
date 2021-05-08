package com.example.tvseries.app.view

import android.graphics.Rect
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseries.R
import com.example.tvseries.app.adapters.EpisodesAdapter
import com.example.tvseries.app.adapters.ShowsAdapter
import com.example.tvseries.app.viewmodel.VMShowDetails
import com.example.tvseries.databinding.FragmentUishowDetailsBinding
import com.example.tvseries.domain.model.Episode
import com.example.tvseries.domain.model.Show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ARG_SHOW = "show"

class UIShowDetails : Fragment() {

    lateinit var binding: FragmentUishowDetailsBinding
    private lateinit var show: Show
    private lateinit var vmDetails: VMShowDetails

    companion object {
        fun newInstance(show: Show) =
            UIShowDetails().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SHOW, show)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            show = it.getParcelable(ARG_SHOW)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_uishow_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmDetails = ViewModelProvider(this).get(VMShowDetails::class.java)
        binding.lifecycleOwner = this

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        Glide.with(view.context)
            .load(show.image?.original)
            .into(binding.ivWallpaper)

        binding.tvTitle.text = show.name
        binding.tvGenres.text = show.genres.joinToString(separator = ", ")
        val schedule = "${show.schedule.time} on ${show.schedule.days.joinToString(separator = ", ")}"
        binding.tvSchedule.text = schedule
        binding.spSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (vmDetails.seasons.value?.isNotEmpty() == true)
                    vmDetails.selectSeason(position+1)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        binding.tvSummary.text = Html.fromHtml(show.summary).toString()

        vmDetails.seasons.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                val list = it.map { season ->
                    String.format(
                        resources.getString(R.string.show_details_season),
                        season.number.toString()
                    )
                }
                binding.spSeason.adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    list
                )
                vmDetails.selectSeason(1)
            }
        })

        vmDetails.episodes.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                vmDetails.selectSeason(1)
            }
        })

        vmDetails.selectedSeason.observe(viewLifecycleOwner, {
            if (it > -1) {
                val list = mutableListOf<Episode>()
                vmDetails.episodes.value!!.forEach { episode ->
                    if (episode.season == it)
                        list.add(episode)
                }
                vmDetails.fillEpisodes(list)
            }
        })

        vmDetails.selectedEpisodes.observe(viewLifecycleOwner, {
            val layout = LinearLayoutManager(requireContext())
            layout.orientation = LinearLayoutManager.HORIZONTAL
            binding.rvEpisodes.layoutManager = layout
            binding.rvEpisodes.adapter = EpisodesAdapter(it) { episode ->
//                val action = UIHomeDirections.actionUIHomeToUIShowDetails(episode)
//                findNavController().navigate(action)
            }
            binding.rvEpisodes.addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)

                        outRect.left = 0
                        outRect.top = 0
                        outRect.right = 0
                        outRect.bottom = 0
                    }
                }
            )
        })

        vmDetails.loadSeasons(requireContext(), show.id)
        vmDetails.loadEpisodes(requireContext(), show.id)
    }
}