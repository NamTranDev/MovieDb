package nam.tran.moviedb.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import nam.tran.data.model.GenreModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterGenreBinding
import tran.nam.common.DataBoundListAdapter

class GenreAdapter(
    private val dataBindingComponent: DataBindingComponent
) : DataBoundListAdapter<GenreModel, AdapterGenreBinding>(object :
    DiffUtil.ItemCallback<GenreModel>() {
    override fun areItemsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GenreModel, newItem: GenreModel): Boolean {
        return oldItem.name == newItem.name
    }

}) {
    override fun createBinding(parent: ViewGroup): AdapterGenreBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_genre,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: AdapterGenreBinding, item: GenreModel) {
        binding.item = item
    }
}