package com.example.tvseries.data

object Consts {

    // Endpoints
    const val BASE_URL = "https://api.tvmaze.com/"
    const val SHOWS_SEARCH = "search/shows"
    const val SHOWS = "shows"
    const val SEASON = "shows/{id}/seasons"
    const val EPISODES = "shows/{id}/episodes"
    const val SHOW = "shows/{id}"
    const val EPISODE = "shows/{id}/episodebynumber"

    // Share
    const val SHARE_SHOW = "${BASE_URL}show/detail/%s"
    const val SHARE_EPISODE = "${BASE_URL}show/episode/%s"
}