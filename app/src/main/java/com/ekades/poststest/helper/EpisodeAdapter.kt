package com.ekades.poststest.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekades.poststest.R

class EpisodeAdapter(private val episodes: List<Episode>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    private fun updateView(episodeId : Long, downloadStatus: DownloadStatus) {
        episodes.forEachIndexed { index, episode ->
            if (episode.id == episodeId) {
                episode.downloadStatus = downloadStatus
                notifyItemChanged(index)
                return@forEachIndexed
            }
        }
    }

    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(episode: Episode) {
            binding.titleView.text = episode.title
            binding.downloadButton.isVisible = (episode.downloadStatus == DownloadStatus.NOT_DOWNLOADED)
            binding.progressView.isVisible = (episode.downloadStatus == DownloadStatus.DOWNLOADING)
            binding.downloadButton.setOnClickListener {
                VidioSDK.downloadEpisode(episode.id) { episodeId, downloadStatus ->
                    updateView(episodeId, downloadStatus)
                }
            }

            Glide.with(context)
                .load(episode.thumbnailUrl)
                .into(binding.lytImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.episode_item,
            parent,
            false
        )

        return EpisodeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size
}