package com.example.tvseries.app.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvseries.data.ApiClient
import com.example.tvseries.domain.model.Episode
import com.example.tvseries.domain.model.Season
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VMShowDetails : ViewModel() {

    val seasons = MutableLiveData(listOf<Season>())
    val episodes = MutableLiveData(listOf<Episode>())
    val selectedSeason = MutableLiveData(-1)
    val selectedEpisodes = MutableLiveData(listOf<Episode>())

    fun fillEpisodes(episodes: List<Episode>) = selectedEpisodes.postValue(episodes)
    fun selectSeason(season: Int) = selectedSeason.postValue(season)

    fun loadSeasons(context: Context, id: Long) {
        val apiClient = ApiClient()
        apiClient.getApiService(context).getSeasons(id)
            .enqueue(object : Callback<List<Season>> {
                override fun onFailure(call: Call<List<Season>>, t: Throwable) {
                    Log.e("SearchError", "error: $t")
                }

                override fun onResponse(call: Call<List<Season>>, response: Response<List<Season>>) {
                    if (response.code() == 200) {
                        val list : List<Season> = response.body()!!
                        seasons.postValue(list)
                    } else {
                        seasons.postValue(listOf())
                    }
                }
            })
    }

    fun loadEpisodes(context: Context, id: Long) {
        val apiClient = ApiClient()
        apiClient.getApiService(context).getEpisodes(id)
            .enqueue(object : Callback<List<Episode>> {
                override fun onFailure(call: Call<List<Episode>>, t: Throwable) {
                    Log.e("SearchError", "error: $t")
                }

                override fun onResponse(call: Call<List<Episode>>, response: Response<List<Episode>>) {
                    if (response.code() == 200) {
                        val result = response.body()?.let{
                            it.map { episode ->
                                episode.apply {
                                    showId = id
                                }
                            }
                        }
                        episodes.postValue(result)
                    } else
                        episodes.postValue(listOf())
                }
            })
    }

}