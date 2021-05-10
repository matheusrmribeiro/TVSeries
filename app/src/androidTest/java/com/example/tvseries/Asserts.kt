package com.example.tvseries

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


fun recyclerViewIsEmpty(): Matcher<View?> {

    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("recycler view is empty")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            return ((view as RecyclerView).adapter?.itemCount ?: 0) == 0
        }
    }
}

fun recyclerViewIsNotEmpty(): Matcher<View?> {

    return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("recycler view is not empty")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            return ((view as RecyclerView).adapter?.itemCount ?: 0) > 0
        }
    }
}