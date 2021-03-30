package nam.tran.moviedb.view.home

import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import nam.tran.data.interactor.IUseCase

class MovieLoader constructor(
    type: MovieType,
    mUseCase: IUseCase,
    mCompositeDisposable: CompositeDisposable
) {

    val request = MutableLiveData<MovieRequest>()

    init {
        request.value = MovieRequest(type)
    }


}