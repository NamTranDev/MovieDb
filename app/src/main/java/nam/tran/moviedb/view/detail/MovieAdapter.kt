package nam.tran.moviedb.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import nam.tran.data.model.GenreModel
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterGenreBinding
import nam.tran.moviedb.databinding.AdapterMovieBinding
import tran.nam.common.DataBoundListAdapter

class MovieAdapter(
    private val dataBindingComponent: DataBindingComponent
) : DataBoundListAdapter<MovieModel, AdapterMovieBinding>(object :
    DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.title == newItem.title
    }

}) {
    override fun createBinding(parent: ViewGroup): AdapterMovieBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_movie,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: AdapterMovieBinding, item: MovieModel) {
        binding.item = item
    }
}