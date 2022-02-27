package reddit.app.repository.api


import kotlinx.coroutines.Deferred
import reddit.app.data.DataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/r/aww/hot.json")
            /** Coroutines - возвращаем данные через Deferred */
    fun searchAsync(
        @Query("limit") loadSize: Int = 0,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Deferred<DataModel>
}