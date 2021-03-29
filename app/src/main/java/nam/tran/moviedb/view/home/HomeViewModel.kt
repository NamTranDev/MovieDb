package nam.tran.moviedb.view.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import nam.tran.data.interactor.IHomeUseCase
import nam.tran.data.model.GenreModel
import nam.tran.data.model.MovieModel
import tran.nam.core.viewmodel.BaseViewModel
import tran.nam.state.Loading
import tran.nam.state.State
import java.util.concurrent.Executors

class HomeViewModel constructor(private val mUseCase: IHomeUseCase) : BaseViewModel() {

    var requestPopular = MutableLiveData<MovieRequest>()
    var requestTopRated = MutableLiveData<MovieRequest>()
    var requestUpcoming = MutableLiveData<MovieRequest>()

    private val mSourceFactoryPopular = Transformations.map(requestPopular) {
        PageMovieSourceFactory(
            it,
            mUseCase,
            mCompositeDisposable
        )
    }

    private val mSourceFactoryTopRated = Transformations.map(requestTopRated) {
        PageMovieSourceFactory(
            it,
            mUseCase,
            mCompositeDisposable
        )
    }

    private val mSourceFactoryUpcoming = Transformations.map(requestUpcoming) {
        PageMovieSourceFactory(
            it,
            mUseCase,
            mCompositeDisposable
        )
    }

    var networkLivePopular: LiveData<State> = Transformations.switchMap(mSourceFactoryPopular) {
        it.networkState
    }

    var networkLiveTopRated: LiveData<State> = Transformations.switchMap(mSourceFactoryTopRated) {
        it.networkState
    }

    var networkLiveUpcoming: LiveData<State> = Transformations.switchMap(mSourceFactoryUpcoming) {
        it.networkState
    }

    var listDataLivePopular: LiveData<PagedList<MovieModel>> =
        Transformations.switchMap(mSourceFactoryPopular) {
            it.toLiveData(
                pageSize = 15,
                fetchExecutor = Executors.newFixedThreadPool(4)
            )
        }

    var listDataLiveTopRated: LiveData<PagedList<MovieModel>> =
        Transformations.switchMap(mSourceFactoryTopRated) {
            it.toLiveData(
                pageSize = 15,
                fetchExecutor = Executors.newFixedThreadPool(4)
            )
        }

    var listDataLiveUpcoming: LiveData<PagedList<MovieModel>> =
        Transformations.switchMap(mSourceFactoryUpcoming) {
            it.toLiveData(
                pageSize = 15,
                fetchExecutor = Executors.newFixedThreadPool(4)
            )
        }

    var emptyDataPopular: LiveData<Boolean> = Transformations.switchMap(mSourceFactoryPopular) {
        it.emptyData
    }

    var emptyDataTopRated: LiveData<Boolean> = Transformations.switchMap(mSourceFactoryTopRated) {
        it.emptyData
    }

    var emptyDataUpcoming: LiveData<Boolean> = Transformations.switchMap(mSourceFactoryUpcoming) {
        it.emptyData
    }

    private val _genreData = MutableLiveData<MutableList<GenreModel>>()
    val genreData: LiveData<MutableList<GenreModel>>
        get() = _genreData

    private val _trendingData = MutableLiveData<MutableList<MovieModel>>()
    val trendingData: LiveData<MutableList<MovieModel>>
        get() = _trendingData

    override fun onInitialized(bundle: Bundle?, isRefresh: Boolean) {
        if (requestPopular.value == null || requestTopRated.value == null || requestUpcoming.value == null || isRefresh) {
            requestPopular.value = MovieRequest(MovieType.POPULAR)
            requestTopRated.value = MovieRequest(MovieType.TOPRATE)
            requestUpcoming.value = MovieRequest(MovieType.UPCOMING)
            execute<Pair<MutableList<MovieModel>, MutableList<GenreModel>>>(
                mUseCase.trendingAndGenre(),
                {
                    _trendingData.value = it?.first
                    _genreData.value = it?.second
                },
                typeLoading = Loading.LOADING_NORMAL
            )
        }
    }
}