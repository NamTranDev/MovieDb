package nam.tran.moviedb.view.home

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.FragmentHomeBinding
import tran.nam.common.ErrorCode
import tran.nam.core.biding.FragmentDataBindingComponent
import tran.nam.core.view.mvvm.BaseFragmentVM
import tran.nam.state.State
import tran.nam.state.Status

class HomeFragment : BaseFragmentVM<FragmentHomeBinding, HomeViewModel>() {

    private val dataBindingComponent = FragmentDataBindingComponent(this)

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pullToRefresh?.setOnRefreshListener {
            mViewModel.onInitialized(arguments, true)
            pullToRefresh?.isRefreshing = false
        }

        val adapterTrending = MovieTrendingPagingAdapter(dataBindingComponent) {
            goToDetail(it)
        }
        rv_trending?.adapter = adapterTrending
        val adapterGenre = GenreAdapter(dataBindingComponent)
        rv_genre?.adapter = adapterGenre
        val adapterPopular = MoviePagingAdapter(dataBindingComponent) {
            goToDetail(it)
        }
        rv_popular?.adapter = adapterPopular
        val adapterTopRated = MoviePagingAdapter(dataBindingComponent) {
            goToDetail(it)
        }
        rv_top_rated?.adapter = adapterTopRated
        val adapterUpcoming = MoviePagingAdapter(dataBindingComponent) {
            goToDetail(it)
        }
        rv_upcoming?.adapter = adapterUpcoming

        mViewDataBinding?.viewModel = mViewModel

        mViewModel.genreData.observe(viewLifecycleOwner, Observer {
            adapterGenre.submitList(it)
        })

        mViewModel.listDataLivePopular.observe(viewLifecycleOwner, Observer {
            adapterPopular.submitList(it)
        })
        mViewModel.listDataLiveTopRated.observe(viewLifecycleOwner, Observer {
            adapterTopRated.submitList(it)
        })
        mViewModel.listDataLiveUpcoming.observe(viewLifecycleOwner, Observer {
            adapterUpcoming.submitList(it)
        })

        mViewModel.networkLivePopular.observe(viewLifecycleOwner, Observer {
            updateStatus(it, adapterPopular, loading_popular, tv_status_popular, rv_popular)
        })
        mViewModel.networkLiveTopRated.observe(viewLifecycleOwner, Observer {
            updateStatus(it, adapterTopRated, loading_top_rated, tv_status_top_rated, rv_top_rated)
        })
        mViewModel.networkLiveUpcoming.observe(viewLifecycleOwner, Observer {
            updateStatus(it, adapterUpcoming, loading_upcoming, tv_status_upcoming, rv_upcoming)
        })

        mViewModel.emptyDataPopular.observe(viewLifecycleOwner, Observer {
            if (!it)
                return@Observer
            tv_status_popular?.visibility = View.VISIBLE
            tv_status_popular?.text = "Not found data"
        })
        mViewModel.emptyDataTopRated.observe(viewLifecycleOwner, Observer {
            if (!it)
                return@Observer
            tv_status_top_rated?.visibility = View.VISIBLE
            tv_status_top_rated?.text = "Not found data"
        })
        mViewModel.emptyDataUpcoming.observe(viewLifecycleOwner, Observer {
            if (!it)
                return@Observer
            tv_status_upcoming?.visibility = View.VISIBLE
            tv_status_upcoming?.text = "Not found data"
        })

    }

    fun goToDetail(movie: MovieModel) {

    }

    private fun updateStatus(
        state: State,
        adapter: INetworkState,
        process: ProgressBar?,
        text: TextView?,
        rv: RecyclerView?
    ) {
        if (state.initial) {
            when (state.status) {
                Status.LOADING -> {
                    rv?.visibility = View.GONE
                    process?.visibility = View.VISIBLE
                    text?.visibility = View.GONE
                }
                Status.ERROR -> {
                    rv?.visibility = View.GONE
                    process?.visibility = View.GONE
                    text?.visibility = View.VISIBLE
                    text?.text = getErrorMessage(state)
                }
                Status.SUCCESS -> {
                    rv?.visibility = View.VISIBLE
                    process?.visibility = View.GONE
                    text?.visibility = View.GONE
                }
            }
        } else {
            adapter.setNetworkState(state)
        }
    }

    fun getErrorMessage(state: State): String {
        return state.errorState?.message ?: when (state.errorState?.code) {
            ErrorCode.SOCKET_TIMEOUT_EXCEPTION.code -> "Không thể thiết lập kết nối đến máy chủ. Vui lòng kiểm tra lại mạng hoặc thử lại sau!"
            ErrorCode.UNKNOWN_HOST_EXCEPTION.code -> "Không thể tìm thấy máy chủ. Vui lòng kiểm tra cài đặt!"
            ErrorCode.SSL_HAND_SHAKE_EXCEPTION.code -> "Không thể tìm thấy máy chủ. Vui lòng kiểm tra cài đặt!"
            ErrorCode.MALFORMED_JSON_EXCEPTION.code -> "Không thể thiết lập kết nối đến máy chủ. Vui lòng kiểm tra lại mạng hoặc thử lại sau!"
            ErrorCode.PARSE_EXCEPTION.code -> "Có lỗi khi xử lý dữ liệu. Vui lòng thử lại sau!"
            else -> "Có lỗi khi kết nối. Vui lòng thao tác lại."
        }
    }
}