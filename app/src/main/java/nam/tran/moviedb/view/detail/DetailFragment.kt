package nam.tran.moviedb.view.detail

import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.FragmentDetailBinding
import tran.nam.core.view.mvvm.BaseFragmentVM

class DetailFragment : BaseFragmentVM<FragmentDetailBinding, DetailViewModel>() {

    companion object{
        const val ARG_MOVIE_ID = "ARG: Movie id"
    }

    override val layoutId: Int
        get() = R.layout.fragment_detail

}