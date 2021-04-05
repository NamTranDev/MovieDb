package nam.tran.data.interactor

import android.os.Build
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import nam.tran.data.api.IApi
import nam.tran.data.model.GenreModel
import nam.tran.data.model.GenreResponse
import nam.tran.data.model.MovieModel
import nam.tran.data.model.MovieResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class UseCaseTest {

    private lateinit var useCase: IUseCase
    private val service = mock<IApi>()

    val apiKey = "a7b3c9975791294647265c71224a88ad"
    val language = "en-US"

    @Before
    fun init() {
        useCase = UseCase(service)
    }

    @Test
    fun trendingAndGenre() {
        whenever(service.trending(apiKey)).thenReturn(
                Observable.just(
                        MovieResponse(
                                arrayListOf(MovieModel(399566, "Godzilla vs. Kong", "", ""),
                                        MovieModel(791373, "Zack Snyder's Justice League", "", ""))
                        )
                )
        )

        whenever(service.genres(apiKey,language)).thenReturn(
                Observable.just(
                        GenreResponse(
                                arrayListOf(GenreModel(28, "Action"),
                                        GenreModel(12, "Adventure"))
                        )
                )
        )

        useCase.trendingAndGenre().test().run {
            Assert.assertEquals(values()[0].first.size,2)
            Assert.assertEquals(values()[0].second.size,2)
        }
    }

    @Test
    fun listTrending() {
        whenever(service.trending(apiKey)).thenReturn(
                Observable.just(
                        MovieResponse(
                                arrayListOf(MovieModel(399566, "Godzilla vs. Kong", "", ""),
                                        MovieModel(791373, "Zack Snyder's Justice League", "", ""))
                        )
                )
        )

        useCase.listTrending().test().run {
            assertNoErrors()
            Assert.assertEquals(values()[0].size,2)
            Assert.assertEquals(values()[0][0].id,399566)
            Assert.assertEquals(values()[0][1].id,791373)
        }
    }

    @Test
    fun listGenre() {
        whenever(service.genres(apiKey,language)).thenReturn(
                Observable.just(
                        GenreResponse(
                                arrayListOf(GenreModel(28, "Action"),
                                        GenreModel(12, "Adventure"))
                        )
                )
        )

        useCase.listGenre().test().run {
            assertNoErrors()
            Assert.assertEquals(values()[0].size,2)
            Assert.assertEquals(values()[0][0].id,28)
            Assert.assertEquals(values()[0][1].id,12)
        }
    }

    @Test
    fun listPopular() {
        whenever(service.populars(apiKey,language,1)).thenReturn(
                Observable.just(
                        MovieResponse(
                                arrayListOf(MovieModel(399566, "Godzilla vs. Kong", "", ""),
                                        MovieModel(791373, "Zack Snyder's Justice League", "", ""))
                        )
                )
        )

        useCase.listPopular(1).test().run {
            assertNoErrors()
            Assert.assertEquals(values()[0].size,2)
            Assert.assertEquals(values()[0][0].id,399566)
            Assert.assertEquals(values()[0][1].id,791373)
        }
    }

    @Test
    fun listTopRated() {
        whenever(service.toprates(apiKey,language,1)).thenReturn(
                Observable.just(
                        MovieResponse(
                                arrayListOf(MovieModel(399566, "Godzilla vs. Kong", "", ""),
                                        MovieModel(791373, "Zack Snyder's Justice League", "", ""))
                        )
                )
        )

        useCase.listTopRated(1).test().run {
            assertNoErrors()
            Assert.assertEquals(values()[0].size,2)
            Assert.assertEquals(values()[0][0].id,399566)
            Assert.assertEquals(values()[0][1].id,791373)
        }
    }

    @Test
    fun listUpComing() {
        whenever(service.upcomings(apiKey,language,1)).thenReturn(
                Observable.just(
                        MovieResponse(
                                arrayListOf(MovieModel(399566, "Godzilla vs. Kong", "", ""),
                                        MovieModel(791373, "Zack Snyder's Justice League", "", ""))
                        )
                )
        )

        useCase.listUpComing(1).test().run {
            assertNoErrors()
            Assert.assertEquals(values()[0].size,2)
            Assert.assertEquals(values()[0][0].id,399566)
            Assert.assertEquals(values()[0][1].id,791373)
        }
    }

    @Test
    fun loadDetail() {
    }

    @Test
    fun videoDetail() {

    }

    @Test
    fun reviewDetail() {

    }

    @Test
    fun recommendationDetail() {

    }
}