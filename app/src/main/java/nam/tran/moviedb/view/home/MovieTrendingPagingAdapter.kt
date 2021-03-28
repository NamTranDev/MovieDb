package nam.tran.moviedb.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterMovieBinding
import nam.tran.moviedb.databinding.AdapterMovieTrendingBinding
import nam.tran.moviedb.databinding.ItemNetworkStateBinding
import tran.nam.state.State

class MovieTrendingPagingAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val goToDetail: (MovieModel) -> Unit
) : PagedListAdapter<MovieModel, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.title == newItem.title
    }
}),INetworkState {

    private var networkState: State? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.adapter_movie_trending -> {
                val holder = MovieTrendingViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.adapter_movie_trending,
                        parent,
                        false,
                        dataBindingComponent
                    )
                )
                holder.binding.root.setOnClickListener {
                    holder.binding.item?.run {
                        goToDetail.invoke(this)
                    }
                }
                return holder
            }
            R.layout.item_network_state -> NetworkStateItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_network_state,
                    parent,
                    false,
                    dataBindingComponent
                )
            )
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.adapter_movie_trending -> (holder as MovieTrendingViewHolder).bind(
                getItem(position)
            )
            R.layout.item_network_state -> (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && !networkState!!.isSuccess()

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun setNetworkState(newNetworkState: State?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.adapter_movie_trending
        }
    }

    class NetworkStateItemViewHolder(val binding: ItemNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(state: State?) {
            binding.state = state
            binding.executePendingBindings()
        }
    }

    class MovieTrendingViewHolder(
        val binding: AdapterMovieTrendingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieModel?) {
            binding.item = movie
            binding.executePendingBindings()
        }
    }
}