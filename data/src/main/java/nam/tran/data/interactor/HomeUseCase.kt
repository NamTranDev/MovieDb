package nam.tran.data.interactor

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function5
import nam.tran.data.api.IApi
import nam.tran.data.model.GenreModel
import nam.tran.data.model.HomeMovieModel
import nam.tran.data.model.MovieModel
import tran.nam.common.Logger
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val iApi: IApi
) : IHomeUseCase {

    val apiKey = "a7b3c9975791294647265c71224a88ad"
    val language = "en-US"

    override fun trendingAndGenre(): Observable<Pair<MutableList<MovieModel>, MutableList<GenreModel>>> {
        return Observable.zip(listTrending(), listGenre(), BiFunction { t1, t2 ->
            Pair(t1, t2)
        })
    }

    override fun listTrending(): Observable<MutableList<MovieModel>> {
        return iApi.trending(apiKey).map {
            it.results
        }
    }

    override fun listGenre(): Observable<MutableList<GenreModel>> {
        return iApi.genres(apiKey, language).map {
            it.genres.forEach {
                it.randomColor()
            }
            it.genres
        }
    }

    override fun listPopular(page: Int): Observable<MutableList<MovieModel>> {
        return iApi.populars(apiKey, language, page).map {
            it.results
        }
    }

    override fun listTopRated(page: Int): Observable<MutableList<MovieModel>> {
        return iApi.toprates(apiKey, language, page).map {
            it.results
        }
    }

    override fun listUpComing(page: Int): Observable<MutableList<MovieModel>> {
        return iApi.upcomings(apiKey, language, page).map {
            it.results
        }
    }

}