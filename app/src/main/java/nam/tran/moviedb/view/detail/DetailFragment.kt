package nam.tran.moviedb.view.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.Dimension
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.fragment_detail.*
import nam.tran.moviedb.R
import nam.tran.moviedb.databinding.FragmentDetailBinding
import nam.tran.moviedb.view.main.MainActivity
import tran.nam.common.Logger
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
        rv_video?.adapter = VideoAdapter(dataBindingComponent)
        rv_review?.adapter = ReviewAdapter(dataBindingComponent)
        rv_recommend?.adapter = MovieAdapter(dataBindingComponent)

        tv_more?.setOnClickListener {
            if (tv_description?.isExpanded == true) {
                tv_description?.collapse()
            } else {
                tv_description?.expand()
            }
        }

        iv_back?.setOnClickListener {
            Logger.debug("Back")
            (activity as? MainActivity)?.onBackPressed()
        }
    }
}