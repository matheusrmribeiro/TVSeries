package com.example.tvseries.domain.interfaces

import com.example.tvseries.data.Consts
import com.example.tvseries.domain.model.Episode
import com.example.tvseries.domain.model.Season
import com.example.tvseries.domain.model.Show
import com.example.tvseries.domain.model.ShowResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(Consts.SHOWS)
    fun getShows(@Query("page") page: Int): Call<List<Show>>

    @GET(Consts.SHOWS_SEARCH)
    fun getShowsFilter(@Query("q") q: String): Call<List<ShowResponse>>

    @GET(Consts.SEASON)
    fun getSeasons(@Path("id") id: Long): Call<List<Season>>

    @GET(Consts.EPISODES)
    fun getEpisodes(@Path("id") id: Long): Call<List<Episode>>

}