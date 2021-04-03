package nam.tran.data.interactor

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function4
import nam.tran.data.api.IApi
import nam.tran.data.model.*
import tran.nam.common.Logger
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UseCase @Inject constructor(
    private val iApi: IApi
) : IUseCase {

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

    override fun loadDetail(id: Long): Observable<MovieDetailModel> {
        return Observable.zip(
            iApi.movieDetail(id, apiKey, language),
            videoDetail(id),
            reviewDetail(id),
            recommendationDetail(id),
            Function4 { movie, video, review, recommendation ->
                Logger.debug("loadDetail")
                movie.videos = video
                movie.reviews = review
                movie.recommendations = recommendation
                movie
            }
        )
    }

    override fun videoDetail(id: Long): Observable<MutableList<VideoModel>> {
        return iApi.videoDetail(id, apiKey, language).map {
            it.videos
        }
    }

    override fun reviewDetail(id: Long): Observable<List<ReviewModel>> {
        return iApi.reviewDetail(id, apiKey, language, 1).map {
            it.reviews.slice(0 until 3).sortedByDescending {
                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                sdf.parse(it.time)
            }
        }
    }

    override fun recommendationDetail(id: Long): Observable<List<MovieModel>> {
        return iApi.recommendationDetail(id, apiKey, language, 1).map {
            it.recommendations.slice(0 until 3)
        }
    }

}