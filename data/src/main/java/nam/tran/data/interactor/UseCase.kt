package nam.tran.data.interactor

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function4
import nam.tran.data.api.IApi
import nam.tran.data.model.*
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

    override fun reviewDetail(id: Long): Observable<MutableList<ReviewModel>> {
        return iApi.reviewDetail(id, apiKey, language, 1).map {
            it.reviews
        }.filter {
            it.size > 2
        }.takeLast(3)
    }

    override fun recommendationDetail(id: Long): Observable<MutableList<RecommendationModel>> {
        return iApi.recommendationDetail(id, apiKey, language, 1).map {
            it.recommendations
        }.filter {
            it.size > 3
        }.takeLast(3)
    }

}