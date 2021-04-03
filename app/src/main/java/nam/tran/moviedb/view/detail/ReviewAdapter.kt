package nam.tran.moviedb.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import nam.tran.data.model.MovieModel
import nam.tran.data.model.ReviewModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterMovieBinding
import nam.tran.moviedb.databinding.AdapterReviewBinding
import tran.nam.common.DataBoundListAdapter

class ReviewAdapter(
    private val dataBindingComponent: DataBindingComponent
) : DataBoundListAdapter<ReviewModel, AdapterReviewBinding>(object :
    DiffUtil.ItemCallback<ReviewModel>() {
    override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
        return oldItem.content == newItem.content
    }

}) {
    override fun createBinding(parent: ViewGroup): AdapterReviewBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_review,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: AdapterReviewBinding, item: ReviewModel) {
        binding.item = item
    }
}