package nam.tran.moviedb.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import io.reactivex.disposables.CompositeDisposable
import nam.tran.data.interactor.IUseCase
import nam.tran.data.model.MovieModel
import tran.nam.common.Logger

class PageMovieSourceFactory constructor(
    private val request: MovieRequest,
    private val mUseCase: IUseCase,
    private val mCompositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieModel>() {

    private val sourceLiveData = MutableLiveData<PageMovieDataSource>()

    val networkState = Transformations.switchMap(sourceLiveData) {
        it.networkState
    }

    val emptyData = Transformations.switchMap(sourceLiveData) {
        it.emptyData
    }

    override fun create(): DataSource<Int, MovieModel> {
        Logger.debug("Paging Learn", "PageDataSourceFactory - create()")
        val source = PageMovieDataSource(request, mUseCase, mCompositeDisposable)
        sourceLiveData.postValue(source)
        return source
    }
}