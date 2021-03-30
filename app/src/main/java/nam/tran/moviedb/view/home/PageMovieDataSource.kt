package nam.tran.moviedb.view.home

import androidx.paging.PageKeyedDataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import nam.tran.data.interactor.IUseCase
import nam.tran.data.model.MovieModel
import tran.nam.common.ErrorResponse
import tran.nam.common.ErrorState
import tran.nam.common.Logger
import tran.nam.common.SingleLiveEvent
import tran.nam.state.State

class PageMovieDataSource constructor(
    val request: MovieRequest,
    private val mUseCase: IUseCase,
    private val mCompositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieModel>() {

    val networkState = SingleLiveEvent<State>()
    val emptyData = SingleLiveEvent<Boolean>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieModel>
    ) {
        val page = 1
        mCompositeDisposable.add(obser(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                emptyData.postValue(false)
                networkState.postValue(State.loading())
            }.subscribe({
                Logger.debug(it)
                networkState.postValue(State.success().apply {
                    hasRefresh = false
                })
                emptyData.postValue(it.isEmpty())
                callback.onResult(it, page, page + 1)
            }, {
                Logger.debug(it)
                val error = if (it is ErrorResponse) {
                    ErrorState(it.messageError, it.code)
                } else {
                    ErrorState.getErrorMessage(it)
                }
                networkState.postValue(State.error(error, retry = {
                    loadInitial(params, callback)
                }))
            })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        mCompositeDisposable.add(obser(params.key).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                networkState.postValue(State.loadingPaging())
            }.subscribe({
                Logger.debug(it)
                networkState.postValue(State.successPaging())
                callback.onResult(it, params.key + 1)
            }, {
                Logger.debug(it)
                val error = if (it is ErrorResponse) {
                    ErrorState(it.messageError, it.code)
                } else {
                    ErrorState.getErrorMessage(it)
                }
                networkState.postValue(State.errorPaging(error, retry = {
                    loadAfter(params, callback)
                }))
            })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieModel>) {
        Logger.debug("loadBefore")
    }

    private fun obser(page: Int): Observable<MutableList<MovieModel>> {
        return when (request.type) {
            MovieType.POPULAR -> {
                mUseCase.listPopular(page)
            }
            MovieType.TOPRATE -> {
                mUseCase.listTopRated(page)
            }
            MovieType.UPCOMING -> {
                mUseCase.listUpComing(page)
            }
        }
    }
}