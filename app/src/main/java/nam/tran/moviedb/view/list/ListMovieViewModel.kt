package nam.tran.moviedb.view.list

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import nam.tran.data.interactor.IUseCase
import nam.tran.data.model.MovieModel
import nam.tran.moviedb.view.home.MovieRequest
import nam.tran.moviedb.view.home.MovieType
import nam.tran.moviedb.view.home.PageMovieSourceFactory
import nam.tran.moviedb.view.list.ListMovieFragment.Companion.ARG_TYPE
import tran.nam.core.viewmodel.BaseViewModel
import tran.nam.state.State
import java.util.concurrent.Executors

class ListMovieViewModel constructor(private val mUseCase: IUseCase) : BaseViewModel() {

    var mTitle = ObservableField<String>()

    private var request = MutableLiveData<MovieRequest>()

    private val mSourceFactory = Transformations.map(request) {
        PageMovieSourceFactory(
            it,
            mUseCase,
            mCompositeDisposable
        )
    }

    var networkState: LiveData<State> = Transformations.switchMap(mSourceFactory) {
        it.networkState
    }

    var listData: LiveData<PagedList<MovieModel>> =
        Transformations.switchMap(mSourceFactory) {
            it.toLiveData(
                pageSize = 15,
                fetchExecutor = Executors.newFixedThreadPool(4)
            )
        }

    override fun onInitialized(bundle: Bundle?, isRefresh: Boolean) {
        val type = bundle?.getString(ARG_TYPE)
        mTitle.set(type)
        if (request.value == null || isRefresh) {
            request.value =
                MovieRequest(type ?: MovieType.POPULAR.value, isHome = false, refresh = isRefresh)
        }
    }
}