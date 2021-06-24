package com.example.tvseries.app.view

import android.graphics.Rect
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseries.R
import com.example.tvseries.app.adapters.EpisodesAdapter
import com.example.tvseries.app.utils.Base64Utils
import com.example.tvseries.app.utils.Share
import com.example.tvseries.app.viewmodel.VMShowDetails
import com.example.tvseries.data.ApiClient
import com.example.tvseries.data.Consts.SHARE_SHOW
import com.example.tvseries.databinding.FragmentUishowDetailsBinding
import com.example.tvseries.domain.model.Episode
import com.example.tvseries.domain.model.Show
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_SHOW = "show"
private const val ARG_ID = "id"

class UIShowDetails : Fragment() {

    lateinit var binding: FragmentUishowDetailsBinding
    private val show = MutableLiveData<Show?>(null)
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
            if (it.containsKey(ARG_ID)) {
                val data = Base64Utils.toJson(it.getString(ARG_ID)!!)
                getShow(data.getString(ARG_ID).toLong())
            } else
                show.postValue(it.getParcelable(ARG_SHOW)!!)
        }
    }

    private fun getShow(id: Long) {
        val apiClient = ApiClient()
        apiClient.getApiService(requireContext()).getShow(id)
            .enqueue(object : Callback<Show> {
                override fun onFailure(call: Call<Show>, t: Throwable) {
                    Log.e("SearchError", "error: $t")
                }

                override fun onResponse(call: Call<Show>, response: Response<Show>) {
                    if (response.code() == 200)
                        show.postValue(response.body()!!)
                }
            })
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

        binding.ivSharing.setOnClickListener {
            val json = JSONObject()
            json.put(ARG_ID, show.value!!.id)
            val data = Base64Utils.toBase64(json.toString())
            Share.shareText(
                requireContext(),
                String.format(SHARE_SHOW, data),
                resources.getString(R.string.show_details_share)
            )
        }

        binding.spSeason.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (vmDetails.seasons.value?.isNotEmpty() == true)
                    vmDetails.selectSeason(position + 1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        show.observe(viewLifecycleOwner) { show ->
            if (show != null) {
                Glide.with(view.context)
                    .load(show.image?.original)
                    .into(binding.ivWallpaper)

                binding.tvTitle.text = show.name
                binding.tvGenres.text = show.genres.joinToString(separator = ", ")
                val schedule =
                    "${show.schedule.time} on ${show.schedule.days.joinToString(separator = ", ")}"
                binding.tvSchedule.text = schedule
                binding.tvSummary.text = Html.fromHtml(show.summary).toString()

                vmDetails.loadSeasons(requireContext(), show.id)
                vmDetails.loadEpisodes(requireContext(), show.id)
            }
        }

        vmDetails.seasons.observe(viewLifecycleOwner) {
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
        }

        vmDetails.episodes.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                vmDetails.selectSeason(1)
            }
        }

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

        vmDetails.selectedEpisodes.observe(viewLifecycleOwner) {
            val layout = LinearLayoutManager(requireContext())
            layout.orientation = LinearLayoutManager.HORIZONTAL
            binding.rvEpisodes.layoutManager = layout
            binding.rvEpisodes.adapter = EpisodesAdapter(it) { episode ->
                val action = UIShowDetailsDirections.actionUIShowDetailsToUIEpisodeDetails(episode)
                findNavController().navigate(action)
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
        }

    }
}