package nam.tran.moviedb.view.detail

import android.os.Bundle
import nam.tran.data.interactor.IUseCase
import nam.tran.data.model.MovieDetailModel
import tran.nam.common.Logger
import tran.nam.core.viewmodel.BaseViewModel

class DetailViewModel constructor(private val mUseCase : IUseCase) : BaseViewModel(){

    override fun onInitialized(bundle: Bundle?, isRefresh: Boolean) {
        execute<MovieDetailModel>(mUseCase.loadDetail(791373),{
            Logger.debug(it)
        })
    }
}