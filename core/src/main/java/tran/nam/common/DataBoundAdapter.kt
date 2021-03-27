package tran.nam.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class DataBoundAdapter<T, V : ViewDataBinding> :
    RecyclerView.Adapter<DataBoundViewHolder<V>>() {

    var data: MutableList<T> = ArrayList()

    open fun updateData(data: MutableList<T>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return data[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val holder = DataBoundViewHolder(createBinding(parent, viewType))
        handleCreateHolder(holder)
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        bind(holder.binding, data[position])
        bind(holder.binding, position)
        handleBindingHolder(holder)
        holder.binding.executePendingBindings()
    }

    fun getPosition(item: T) = data.indexOf(item)

    protected abstract fun bind(binding: V, item: T)
    open fun bind(binding: V, position : Int){}
    protected abstract fun createBinding(parent: ViewGroup, type: Int): V
    open fun handleCreateHolder(holder: DataBoundViewHolder<V>) {}
    open fun handleBindingHolder(holder: DataBoundViewHolder<V>) {}
}