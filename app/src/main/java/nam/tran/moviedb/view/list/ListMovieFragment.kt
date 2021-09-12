package nam.tran.moviedb.view.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_list_movie.*
import kotlinx.android.synthetic.main.fragment_list_movie.pullToRefresh
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.FragmentListMovieBinding
import nam.tran.moviedb.view.detail.DetailFragment
import tran.nam.core.biding.FragmentDataBindingComponent
import tran.nam.core.view.mvvm.BaseFragmentVM

class ListMovieFragment : BaseFragmentVM<FragmentListMovieBinding, ListMovieViewModel>() {

    companion object {
        const val ARG_TYPE = "Arg : Type Movie"
        const val ARG_EPOXY = "Arg : Epoxy"
    }

    private val dataBindingComponent = FragmentDataBindingComponent(this)

    override val layoutId: Int
        get() = R.layout.fragment_list_movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pullToRefresh?.setOnRefreshListener {
            mViewModel.onInitialized(arguments, true)
            pullToRefresh?.isRefreshing = false
        }

        mViewDataBinding?.viewModel = mViewModel

        val adapterMovie = MoviePagingListAdapter(dataBindingComponent) {
            goToDetail(it)
        }

        val listMovieController = EpoxyMoviePagingAdapter(){
            goToDetail(it)
        }

        val isEpoxy = arguments?.getBoolean(ARG_EPOXY)

        rv_movie?.adapter = if (isEpoxy == true) listMovieController.adapter else adapterMovie
//        val layoutManager = rv_movie.layoutManager as? GridLayoutManager
//        layoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return when (adapterMovie.getItemViewType(position)) {
//                    R.layout.adapter_network_state -> 2
//                    else -> 1
//                }
//            }
//        }

        mViewModel.listData.observe(viewLifecycleOwner, Observer {
            if (isEpoxy == true)
                listMovieController.submitList(it)
            else
                adapterMovie.submitList(it)
        })

        mViewModel.networkState.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.initial) {
                    mViewModel.status.set(it)
                } else {
                    adapterMovie.setNetworkState(it)
                }
            }
        })
    }

    private fun goToDetail(movie: MovieModel) {
        findNavController().navigate(
            R.id.action_listNormalFragment_to_detailFragment,
            Bundle().apply {
                putLong(DetailFragment.ARG_MOVIE_ID, movie.id)
            })
    }
}