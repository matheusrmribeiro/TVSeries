package com.example.tvseries.app.viewmodel

import android.content.Context
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.tvseries.data.ApiClient
import com.example.tvseries.domain.model.Show
import com.example.tvseries.domain.model.ShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.floor
import kotlin.math.round

class VMHome : ViewModel() {

    val shows = MutableLiveData(listOf<Show>())
    var pageId: Int = 0
    private val _isSearchMode = MutableLiveData(false)

    val showSearchImage: LiveData<Boolean> = Transformations.map(shows) {
        it.isEmpty() && (_isSearchMode.value == true)
    }

    fun setSearchMode(value: Boolean) = _isSearchMode.postValue(value)
    fun isSearchMode() = _isSearchMode.value == true

    fun clearShows() {
        shows.postValue(listOf())
        pageId = 0
    }

    fun search(context: Context, textQuery: String) {
        val apiClient = ApiClient()
        apiClient.getApiService(context).getShowsFilter(textQuery)
            .enqueue(object : Callback<List<ShowResponse>> {
                override fun onFailure(call: Call<List<ShowResponse>>, t: Throwable) {
                    Log.e("SearchError", "error: $t")
                }

                override fun onResponse(call: Call<List<ShowResponse>>, response: Response<List<ShowResponse>>) {
                    if (response.code() == 200) {
                        val list : List<Show> = response.body()!!.map { it.show }
                        shows.postValue(list)
                    } else {
                        shows.postValue(listOf())
                    }
                }
            })
    }

    fun all(context: Context) {
        val apiClient = ApiClient()
        apiClient.getApiService(context).getShows(pageId)
            .enqueue(object : Callback<List<Show>> {
                override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                    Log.e("SearchError", "error: $t")
                }

                override fun onResponse(call: Call<List<Show>>, response: Response<List<Show>>) {
                    if (response.code() == 200) {
                        shows.postValue(response.body() as List<Show>)
                    } else {
                        shows.postValue(listOf())
                    }
                }
            })
        pageId++
    }

}