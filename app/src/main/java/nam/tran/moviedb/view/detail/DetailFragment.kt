package nam.tran.moviedb.view.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.Dimension
import kotlinx.android.synthetic.main.fragment_detail.*
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.FragmentDetailBinding
import tran.nam.core.biding.FragmentDataBindingComponent
import tran.nam.core.view.mvvm.BaseFragmentVM

class DetailFragment : BaseFragmentVM<FragmentDetailBinding, DetailViewModel>() {

    companion object {
        const val ARG_MOVIE_ID = "ARG: Movie id"
    }

    private val dataBindingComponent = FragmentDataBindingComponent(this)

    override val layoutId: Int
        get() = R.layout.fragment_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewDataBinding?.viewModel = mViewModel
        rv_category?.adapter = CategoryAdapter(dataBindingComponent)
    }
}