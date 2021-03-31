package nam.tran.moviedb.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import nam.tran.data.model.CategoryModel
import nam.tran.data.model.GenreModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.AdapterCategoryBinding
import nam.tran.moviedb.databinding.AdapterGenreBinding
import tran.nam.common.DataBoundListAdapter

class CategoryAdapter(
    private val dataBindingComponent: DataBindingComponent
) : DataBoundListAdapter<CategoryModel, AdapterCategoryBinding>(object :
    DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        return oldItem.name == newItem.name
    }

}) {
    override fun createBinding(parent: ViewGroup): AdapterCategoryBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.adapter_category,
            parent,
            false,
            dataBindingComponent
        )
    }

    override fun bind(binding: AdapterCategoryBinding, item: CategoryModel) {
        binding.item = item
    }
}