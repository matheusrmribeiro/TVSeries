package com.example.tvseries.app.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.tvseries.R
import com.example.tvseries.recyclerViewIsNotEmpty
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UIHomeTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit  var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @Test
    fun test_load_data() {

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(
                   """
                        [
                            {
                              "id": 1,
                              "url": "https://www.tvmaze.com/shows/1/under-the-dome",
                              "name": "Under the Dome",
                              "type": "Scripted",
                              "language": "English",
                              "genres": [
                                "Drama",
                                "Science-Fiction",
                                "Thriller"
                              ],
                              "status": "Ended",
                              "runtime": 60,
                              "premiered": "2013-06-24",
                              "officialSite": "http://www.cbs.com/shows/under-the-dome/",
                              "schedule": {
                                "time": "22:00",
                                "days": [
                                  "Thursday"
                                ]
                              },
                              "rating": {
                                "average": 6.6
                              },
                              "weight": 96,
                              "network": {
                                "id": 2,
                                "name": "CBS",
                                "country": {
                                  "name": "United States",
                                  "code": "US",
                                  "timezone": "America/New_York"
                                }
                              },
                              "webChannel": null,
                              "dvdCountry": null,
                              "externals": {
                                "tvrage": 25988,
                                "thetvdb": 264492,
                                "imdb": "tt1553656"
                              },
                              "image": {
                                "medium": "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                                "original": "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
                              },
                              "summary": "<p><b>Under the Dome</b> is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away.</p>",
                              "updated": 1617697381,
                              "_links": {
                                "self": {
                                  "href": "https://api.tvmaze.com/shows/1"
                                },
                                "previousepisode": {
                                  "href": "https://api.tvmaze.com/episodes/185054"
                                }
                              }
                            },
                            
                              "id": 2,
                              "url": "https://www.tvmaze.com/shows/2/person-of-interest",
                              "name": "Person of Interest",
                              "type": "Scripted",
                              "language": "English",
                              "genres": [
                                "Action",
                                "Crime",
                                "Science-Fiction"
                              ],
                              "status": "Ended",
                              "runtime": 60,
                              "premiered": "2011-09-22",
                              "officialSite": "http://www.cbs.com/shows/person_of_interest/",
                              "schedule": {
                                "time": "22:00",
                                "days": [
                                  "Tuesday"
                                ]
                              },
                              "rating": {
                                "average": 8.9
                              },
                              "weight": 92,
                              "network": {
                                "id": 2,
                                "name": "CBS",
                                "country": {
                                  "name": "United States",
                                  "code": "US",
                                  "timezone": "America/New_York"
                                }
                              },
                              "webChannel": null,
                              "dvdCountry": null,
                              "externals": {
                                "tvrage": 28376,
                                "thetvdb": 248742,
                                "imdb": "tt1839578"
                              },
                              "image": {
                                "medium": "https://static.tvmaze.com/uploads/images/medium_portrait/163/407679.jpg",
                                "original": "https://static.tvmaze.com/uploads/images/original_untouched/163/407679.jpg"
                              },
                              "summary": "<p>You are being watched. The government has a secret system, a machine that spies on you every hour of every day. I know because I built it. I designed the Machine to detect acts of terror but it sees everything. Violent crimes involving ordinary people. People like you. Crimes the government considered \"irrelevant\". They wouldn't act so I decided I would. But I needed a partner. Someone with the skills to intervene. Hunted by the authorities, we work in secret. You'll never find us. But victim or perpetrator, if your number is up, we'll find you.</p>",
                              "updated": 1588773151,
                              "_links": {
                                "self": {
                                  "href": "https://api.tvmaze.com/shows/2"
                                },
                                "previousepisode": {
                                  "href": "https://api.tvmaze.com/episodes/659372"
                                }
                              }
                            }
                        ]
                   """.trimIndent()
                )
        )

        val scenario = launchFragmentInContainer<UIHome>(themeResId = R.style.Theme_MaterialComponents)
        var fragment : UIHome
        scenario.onFragment {
            onView(withId(R.id.ivSearch)).check(matches(not(isDisplayed())))
            onView(withId(R.id.rvShows)).check(matches(isDisplayed()))
            onView(withId(R.id.rvShows)).check(matches(recyclerViewIsNotEmpty()))        }

    }

}