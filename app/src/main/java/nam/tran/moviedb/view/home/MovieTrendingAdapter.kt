package nam.tran.moviedb.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterMovieTrendingBinding
import tran.nam.common.DataBoundListAdapter

class MovieTrendingAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val goToDetail: (MovieModel) -> Unit
) : DataBoundListAdapter<MovieModel, AdapterMovieTrendingBinding>(object :
    DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.title == newItem.title
    }
}) {
    override fun createBinding(parent: ViewGroup): AdapterMovieTrendingBinding {
        val binding = DataBindingUtil.inflate<AdapterMovieTrendingBinding>(
            LayoutInflater.from(parent.context),
            R.layout.adapter_movie_trending,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.item?.run {
                goToDetail.invoke(this)
            }
        }
        return binding
    }

    override fun bind(binding: AdapterMovieTrendingBinding, item: MovieModel) {
        binding.item = item
    }

}