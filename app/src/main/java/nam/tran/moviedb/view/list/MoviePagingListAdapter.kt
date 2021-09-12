package nam.tran.moviedb.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterMovieListBinding
import nam.tran.moviedb.databinding.AdapterNetworkStateBinding
import tran.nam.state.State

class MoviePagingListAdapter(
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
}) {

    private var networkState: State? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.adapter_movie_list -> {
                val holder = MovieViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.adapter_movie_list,
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
            R.layout.adapter_network_state -> NetworkStateItemViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.adapter_network_state,
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
            R.layout.adapter_movie_list -> (holder as MovieViewHolder).bind(
                getItem(position)
            )
            R.layout.adapter_network_state -> (holder as NetworkStateItemViewHolder).bind(
                networkState
            )
        }
    }

    private fun hasExtraRow() = networkState != null && !networkState!!.isSuccess()

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: State?) {
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
            R.layout.adapter_network_state
        } else {
            R.layout.adapter_movie_list
        }
    }

    class NetworkStateItemViewHolder(val binding: AdapterNetworkStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(state: State?) {
            binding.state = state
            binding.executePendingBindings()
        }
    }

    class MovieViewHolder(
        val binding: AdapterMovieListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieModel?) {
            binding.item = movie
            binding.executePendingBindings()
        }
    }
}