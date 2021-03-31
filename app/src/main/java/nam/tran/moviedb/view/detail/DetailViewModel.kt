package nam.tran.moviedb.view.detail

import android.os.Bundle
import androidx.databinding.ObservableField
import nam.tran.data.interactor.IUseCase
import nam.tran.data.model.MovieDetailModel
import nam.tran.moviedb.view.detail.DetailFragment.Companion.ARG_MOVIE_ID
import tran.nam.common.Logger
import tran.nam.core.viewmodel.BaseViewModel
import tran.nam.state.Loading

class DetailViewModel constructor(private val mUseCase : IUseCase) : BaseViewModel(){

    val mDetail = ObservableField<MovieDetailModel>()

    override fun onInitialized(bundle: Bundle?, isRefresh: Boolean) {
        bundle?.getLong(ARG_MOVIE_ID)?.run {
            execute<MovieDetailModel>(mUseCase.loadDetail(this),{
                Logger.debug(it)
                mDetail.set(it)
            })
        }
    }
}