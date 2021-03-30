package nam.tran.data.interactor

import io.reactivex.Observable
import nam.tran.data.model.*

interface IUseCase {
    fun trendingAndGenre(): Observable<Pair<MutableList<MovieModel>, MutableList<GenreModel>>>
    fun listTrending(): Observable<MutableList<MovieModel>>
    fun listGenre(): Observable<MutableList<GenreModel>>
    fun listPopular(page: Int): Observable<MutableList<MovieModel>>
    fun listTopRated(page: Int): Observable<MutableList<MovieModel>>
    fun listUpComing(page: Int): Observable<MutableList<MovieModel>>

    fun loadDetail(id: Long): Observable<MovieDetailModel>
    fun videoDetail(id: Long): Observable<MutableList<VideoModel>>
    fun reviewDetail(id: Long): Observable<MutableList<ReviewModel>>
    fun recommendationDetail(id: Long): Observable<MutableList<RecommendationModel>>
}