package com.example.tvseries.app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tvseries.domain.model.Show
import com.example.tvseries.getOrAwaitValue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class ExampleUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val vmHome = VMHome()

    @Test
    fun test_initialize() {
        Assert.assertEquals(vmHome.shows.value, listOf<Show>())
        Assert.assertEquals(vmHome.pageId, 0)
        Assert.assertEquals(vmHome.showSearchImage.getOrAwaitValue(), false)
    }

    @Test
    fun test_set_search_mode() {
        vmHome.setSearchMode(true)
        Assert.assertEquals(vmHome.showSearchImage.getOrAwaitValue(), true)
    }

    @Test
    fun test_clear_shows() {
        vmHome.clearShows()
        Assert.assertEquals(vmHome.showSearchImage.getOrAwaitValue(), true)
    }


}