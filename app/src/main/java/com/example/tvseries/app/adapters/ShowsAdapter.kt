package com.example.tvseries.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseries.R
import com.example.tvseries.domain.model.Show
import kotlinx.android.synthetic.main.fragment_uihome_show_item.view.*

class ShowsAdapter(
    private val onClickCallback: (show: Show) -> Unit,
    private val getMoreCallback: () -> Unit
) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<Show>()

    fun addItems(items: List<Show>) {
        dataSet.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Show, onClickCallback: (enterprise: Show) -> Unit) {
            view.tvTitle.text = data.name
            if (data.genres.isNotEmpty()) {
                var genres = data.genres[0]
                if (data.genres.size > 1)
                    genres += ", ${data.genres[1]}"
                view.tvSubTitle.text = genres
            }
            view.setOnClickListener {
                data.loadedBitmap = view.ivWallpaper.drawable.toBitmap()
                onClickCallback.invoke(data)
            }
            view.tvRating.text = data.rating?.average.toString()
            if (data.image?.medium?.isNotEmpty() == true) {
                Glide.with(view.context)
                .load(data.image.medium)
                .into(view.ivWallpaper)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_uihome_show_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item) {
            onClickCallback.invoke(item)
        }

        if (position == itemCount -1)
            getMoreCallback.invoke()
    }


}