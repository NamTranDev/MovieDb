package nam.tran.data.api

import nam.tran.data.testing.DependencyProvider
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class ApiTest {

    private lateinit var iApi: IApi
    private lateinit var mockWebServer: MockWebServer
    val apiKey = "a7b3c9975791294647265c71224a88ad"
    val language = "en-US"

    @Before
    fun init() {
        mockWebServer = MockWebServer()
        iApi = DependencyProvider
            .getRetrofit(mockWebServer.url("/"))
            .create(IApi::class.java)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get trending response`(){
        queueResponse {
            setResponseCode(200)
            setBody(DependencyProvider.getResponseFromJson("trending"))
        }

        iApi
            .trending(apiKey)
            .test()
            .run {
                assertNoErrors()
                Assert.assertEquals(values().size,20)
            }
    }

    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}