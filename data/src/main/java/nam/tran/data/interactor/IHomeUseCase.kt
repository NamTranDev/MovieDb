package nam.tran.data.interactor

import io.reactivex.Observable
import nam.tran.data.model.GenreModel
import nam.tran.data.model.HomeMovieModel
import nam.tran.data.model.MovieModel

interface IHomeUseCase  {
    fun trendingAndGenre() : Observable<Pair<MutableList<MovieModel>,MutableList<GenreModel>>>
    fun listTrending() : Observable<MutableList<MovieModel>>
    fun listGenre() : Observable<MutableList<GenreModel>>
    fun listPopular(page : Int) : Observable<MutableList<MovieModel>>
    fun listTopRated(page : Int) : Observable<MutableList<MovieModel>>
    fun listUpComing(page : Int) : Observable<MutableList<MovieModel>>
}