package nam.tran.data.api

import io.reactivex.Observable
import nam.tran.data.model.GenreResponse
import nam.tran.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IApi {
    @GET("/3/trending/movie/week")
    fun trending(@Query("api_key") key: String): Observable<MovieResponse>

    @GET("/3/genre/movie/list")
    fun genres(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): Observable<GenreResponse>

    @GET("/3/movie/popular")
    fun populars(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MovieResponse>

    @GET("/3/movie/top_rated")
    fun toprates(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MovieResponse>

    @GET("/3/movie/upcoming")
    fun upcomings(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Observable<MovieResponse>
}
