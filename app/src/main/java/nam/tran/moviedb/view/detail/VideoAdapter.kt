package nam.tran.moviedb.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import nam.tran.data.model.VideoModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterVideoBinding
import tran.nam.common.DataBoundListAdapter

class VideoAdapter(
    private val dataBindingComponent: DataBindingComponent
) : DataBoundListAdapter<VideoModel, AdapterVideoBinding>(object :
    DiffUtil.ItemCallback<VideoModel>() {
    override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
        return oldItem.key == newItem.key
    }

}) {
    override fun createBinding(parent: ViewGroup): AdapterVideoBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_video,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: AdapterVideoBinding, item: VideoModel) {
        binding.item = item
    }
}