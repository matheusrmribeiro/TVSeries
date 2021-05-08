package com.example.tvseries.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseries.R
import com.example.tvseries.domain.model.Episode
import kotlinx.android.synthetic.main.fragment_uihome_show_item.view.*

class EpisodesAdapter(
    private val dataSet: List<Episode>,
    private val onClickCallback: (episode: Episode) -> Unit
) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Episode, onClickCallback: (episode: Episode) -> Unit) {
            view.tvTitle.text = data.name
            view.tvSubTitle.text = String.format(
                view.resources.getString(R.string.show_details_episode),
                data.number.toString()
            )
            view.setOnClickListener {
                onClickCallback.invoke(data)
            }
            if (data.image?.medium?.isNotEmpty() == true) {
                Glide.with(view.context)
                    .load(data.image.medium)
                    .into(view.ivWallpaper)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_uishow_details_episode_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item) {
            onClickCallback.invoke(item)
        }
    }


}