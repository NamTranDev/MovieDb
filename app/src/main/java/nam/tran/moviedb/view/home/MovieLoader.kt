package nam.tran.moviedb.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.reactivex.disposables.CompositeDisposable
import nam.tran.data.interactor.IHomeUseCase
import nam.tran.data.model.MovieModel
import tran.nam.state.State
import java.util.concurrent.Executors

class MovieLoader constructor(
    type: MovieType,
    mUseCase: IHomeUseCase,
    mCompositeDisposable: CompositeDisposable
) {

    val request = MutableLiveData<MovieRequest>()

    init {
        request.value = MovieRequest(type)
    }


}