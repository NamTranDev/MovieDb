@file:Suppress("unused")

package tran.nam.core.biding

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import tran.nam.state.State
import tran.nam.state.Status

object BidingCommon {

    @JvmStatic
    @BindingAdapter("visibleView")
    fun visibleView(view: View, state: State?) {
        state?.let {
            when (it.status) {
                Status.ERROR,
                Status.LOADING -> {
                    view.visibility = View.GONE
                }
                Status.SUCCESS -> view.visibility = View.VISIBLE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("refreshView")
    fun refreshView(view: SwipeRefreshLayout, state: State?) {
        state?.let {
            when (it.status) {
                Status.LOADING -> if (it.hasRefresh) view.isRefreshing = true
                Status.ERROR, Status.SUCCESS -> if (it.hasRefresh) view.isRefreshing = false
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visibleProgress")
    fun visibleProgress(view: View, state: State?) {
        state?.let {
            when (it.status) {
                Status.ERROR, Status.SUCCESS -> view.visibility = View.GONE
                Status.LOADING -> {
                    if (!state.hasRefresh)
                        view.visibility = View.VISIBLE
                    else
                        view.visibility = View.GONE
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visibleTextError")
    fun visibleTextError(text: TextView, state: State?) {
        state?.let {
            when (it.status) {
                Status.ERROR -> {
                    text.visibility = View.VISIBLE
                    text.text = it.errorState?.message
                }
                Status.LOADING, Status.SUCCESS -> text.visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visibleButtonError")
    fun visibleButtonError(bt: Button, state: State?) {
        state?.let {
            when (it.status) {
                Status.ERROR -> {
                    if (it.hasRefresh){
                        bt.visibility = View.GONE
                    }else{
                        bt.visibility = View.VISIBLE
                        bt.setOnClickListener {
                            state.retry?.invoke()
                        }
                    }
                }
                Status.LOADING, Status.SUCCESS -> bt.visibility = View.GONE
            }
        }
    }
}
