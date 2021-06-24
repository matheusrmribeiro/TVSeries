package com.example.tvseries.app.view

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.tvseries.R
import com.example.tvseries.app.utils.Base64Utils
import com.example.tvseries.app.utils.Share
import com.example.tvseries.data.ApiClient
import com.example.tvseries.data.Consts
import com.example.tvseries.databinding.FragmentUiepisodeDetailsBinding
import com.example.tvseries.domain.model.Episode
import com.example.tvseries.domain.model.Show
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_EPISODE = "episode"
private const val ARG_ID = "id"
private const val ARG_SEASON = "season"
private const val ARG_NUMBER = "number"

class UIEpisodeDetails : Fragment() {
    lateinit var binding: FragmentUiepisodeDetailsBinding
    private val episode = MutableLiveData<Episode?>(null)

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
            if (it.containsKey(ARG_ID)) {
                val data = Base64Utils.toJson(it.getString(ARG_ID)!!)
                getEpisode(
                    data.getString(ARG_ID).toLong(),
                    data.getString(ARG_SEASON).toLong(),
                    data.getString(ARG_NUMBER).toLong(),
                )
            } else
                episode.postValue(it.getParcelable(ARG_EPISODE)!!)
        }
    }

    private fun getEpisode(id: Long, seasonId: Long, numberId: Long) {
        val apiClient = ApiClient()
        apiClient.getApiService(requireContext()).getEpisode(id, seasonId, numberId)
            .enqueue(object : Callback<Episode> {
                override fun onFailure(call: Call<Episode>, t: Throwable) {
                    Log.e("SearchError", "error: $t")
                }

                override fun onResponse(call: Call<Episode>, response: Response<Episode>) {
                    if (response.code() == 200)
                        episode.postValue(response.body()!!)
                }
            })
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

        binding.ivSharing.setOnClickListener {
            val json = JSONObject()
            json.put(ARG_ID, episode.value!!.showId)
            json.put(ARG_SEASON, episode.value!!.season)
            json.put(ARG_NUMBER, episode.value!!.number)
            val data = Base64Utils.toBase64(json.toString())
            Share.shareText(
                requireContext(),
                String.format(Consts.SHARE_EPISODE, data),
                resources.getString(R.string.episode_details_share)
            )
        }

        episode.observe(viewLifecycleOwner) { episode ->
            if (episode != null) {
                Glide.with(view.context)
                    .load(episode.image?.original)
                    .into(binding.ivWallpaper)

                binding.tvTitle.text = episode.name
                binding.tvSubtitle.text = String.format(resources.getString(R.string.episode_details_episode), episode.number.toString())
                binding.tvSeason.text = String.format(resources.getString(R.string.episode_details_season), episode.season.toString())
                binding.tvSummary.text = Html.fromHtml(episode.summary).toString()
            }
        }

    }

}